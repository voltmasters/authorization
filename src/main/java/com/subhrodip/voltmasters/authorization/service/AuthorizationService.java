package com.subhrodip.voltmasters.authorization.service;

import com.subhrodip.voltmasters.authorization.config.KafkaTopics;
import com.subhrodip.voltmasters.authorization.model.AuthorizationRequest;
import com.subhrodip.voltmasters.authorization.model.AuthorizationResponse;
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
    KStream<String, AuthorizationRequest> stream =
        streamsBuilder.stream(
            KafkaTopics.CHARGE_AUTHORIZATION_INPUT_TOPIC,
            Consumed.with(
                Serdes.String(),
                Serdes.serdeFrom(
                    new JsonSerializer<>(),
                    new ErrorHandlingDeserializer<>(
                        new JsonDeserializer<>(AuthorizationRequest.class)))));

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

  private AuthorizationResponse processAuthorization(AuthorizationRequest request) {
    if (request.driverIdentifier() == null || request.driverIdentifier().id() == null) {
      return new AuthorizationResponse(DriverStatus.Invalid);
    }

    String driverId = request.driverIdentifier().id();
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
