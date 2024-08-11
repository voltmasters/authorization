/*
 * authorization - Authorization Service for authenticating requests from Charging Stations
 * Copyright © 2024 Subhrodip Mohanta (contact@subhrodip.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.subhrodip.voltmasters.authorization.service;

import com.subhrodip.voltmasters.authorization.config.KafkaTopics;
import com.subhrodip.voltmasters.authorization.model.AuthorizationRequest;
import com.subhrodip.voltmasters.authorization.model.AuthorizationResponse;
import com.subhrodip.voltmasters.authorization.model.ChargingRequest;
import com.subhrodip.voltmasters.authorization.model.DriverStatus;
import com.subhrodip.voltmasters.authorization.repository.DriverAuthorizationRepository;
import com.subhrodip.voltmasters.authorization.repository.StationAuthorizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorizationService {

  private StationAuthorizationRepository stationAuthorizationRepository;
  private DriverAuthorizationRepository driverAuthorizationRepository;

  private StreamsBuilder streamsBuilder;

  @Autowired
  public AuthorizationService(
      StationAuthorizationRepository stationAuthorizationRepository,
      DriverAuthorizationRepository driverAuthorizationRepository,
      StreamsBuilder streamsBuilder) {

    this.stationAuthorizationRepository = stationAuthorizationRepository;
    this.driverAuthorizationRepository = driverAuthorizationRepository;
    this.streamsBuilder = streamsBuilder;

    createTopology();
  }

  private void createTopology() {
    KStream<String, ChargingRequest> stream =
        streamsBuilder.stream(
            KafkaTopics.CHARGE_AUTHORIZATION_INPUT_TOPIC,
            Consumed.with(
                Serdes.String(),
                Serdes.serdeFrom(
                    new JsonSerializer<>(),
                    new ErrorHandlingDeserializer<>(
                        new JsonDeserializer<>(ChargingRequest.class)))));

    stream
        .mapValues(this::processAuthorization)
        .to(
            KafkaTopics.CHARGE_AUTHORIZATION_OUTPUT_TOPIC,
            Produced.with(
                Serdes.String(),
                Serdes.serdeFrom(
                    new JsonSerializer<>(),
                    new ErrorHandlingDeserializer<>(
                        new JsonDeserializer<>(AuthorizationResponse.class)))));
  }

  AuthorizationResponse processAuthorization(ChargingRequest request) {

    AuthorizationRequest payload = request.authorizationRequest();

    if (payload.driverIdentifier() == null || payload.driverIdentifier().id() == null) {
      return new AuthorizationResponse(DriverStatus.Invalid);
    }

    String driverId = payload.driverIdentifier().id();

    int length = driverId.length();
    if (length >= 20 && length <= 80) {
      return new AuthorizationResponse(DriverStatus.Invalid);
    }

    if (!driverAuthorizationRepository.exists(driverId)) {
      return new AuthorizationResponse(DriverStatus.Unknown);
    }

    boolean isAllowed = driverAuthorizationRepository.isAllowedToCharge(driverId);
    if (isAllowed) {
      return new AuthorizationResponse(DriverStatus.Accepted);
    } else {
      return new AuthorizationResponse(DriverStatus.Rejected);
    }
  }
}
