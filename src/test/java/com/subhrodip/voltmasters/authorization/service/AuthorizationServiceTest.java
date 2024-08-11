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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.subhrodip.voltmasters.authorization.model.*;
import com.subhrodip.voltmasters.authorization.repository.DriverAuthorizationRepository;
import com.subhrodip.voltmasters.authorization.repository.StationAuthorizationRepository;
import java.util.UUID;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled("Need to take a look afterwords to fix the test")
public class AuthorizationServiceTest {

  @Mock private StationAuthorizationRepository stationAuthorizationRepository;

  @Mock private DriverAuthorizationRepository driverAuthorizationRepository;

  @Mock private StreamsBuilder streamsBuilder;

  @Mock private KStream<String, ChargingRequest> kStream;

  @InjectMocks private AuthorizationService authorizationService;

  @BeforeEach
  public void setUp() {
    when(streamsBuilder.stream(anyString(), any(Consumed.class))).thenReturn(kStream);
    authorizationService =
        new AuthorizationService(
            stationAuthorizationRepository, driverAuthorizationRepository, streamsBuilder);
  }

  @Test
  public void processAuthorizationDriverIdentifierNull() {
    ChargingRequest request =
        new ChargingRequest(UUID.randomUUID(), new AuthorizationRequest(UUID.randomUUID(), null));
    AuthorizationResponse response = authorizationService.processAuthorization(request);
    assertEquals(DriverStatus.Invalid, response.authorizationStatus());
  }

  @Test
  public void processAuthorizationDriverIdentifierIdNull() {
    ChargingRequest request =
        new ChargingRequest(
            UUID.randomUUID(),
            new AuthorizationRequest(UUID.randomUUID(), new DriverIdentifier(null)));
    AuthorizationResponse response = authorizationService.processAuthorization(request);
    assertEquals(DriverStatus.Invalid, response.authorizationStatus());
  }

  @Test
  public void processAuthorizationDriverIdentifierIdLengthInvalid() {
    String invalidId = "a".repeat(25);
    ChargingRequest request =
        new ChargingRequest(
            UUID.randomUUID(),
            new AuthorizationRequest(UUID.randomUUID(), new DriverIdentifier(invalidId)));
    AuthorizationResponse response = authorizationService.processAuthorization(request);
    assertEquals(DriverStatus.Invalid, response.authorizationStatus());
  }

  @Test
  public void processAuthorizationDriverIdentifierUnknown() {
    String unknownId = "unknown-id";
    when(driverAuthorizationRepository.exists(unknownId)).thenReturn(false);
    ChargingRequest request =
        new ChargingRequest(
            UUID.randomUUID(),
            new AuthorizationRequest(UUID.randomUUID(), new DriverIdentifier(unknownId)));
    AuthorizationResponse response = authorizationService.processAuthorization(request);
    assertEquals(DriverStatus.Unknown, response.authorizationStatus());
  }

  @Test
  public void processAuthorizationDriverIdentifierAccepted() {
    String validId = "valid-id";
    when(driverAuthorizationRepository.exists(validId)).thenReturn(true);
    when(driverAuthorizationRepository.isAllowedToCharge(validId)).thenReturn(true);
    ChargingRequest request =
        new ChargingRequest(
            UUID.randomUUID(),
            new AuthorizationRequest(UUID.randomUUID(), new DriverIdentifier(validId)));
    AuthorizationResponse response = authorizationService.processAuthorization(request);
    assertEquals(DriverStatus.Accepted, response.authorizationStatus());
  }

  @Test
  public void processAuthorizationDriverIdentifierRejected() {
    String validId = "valid-id";
    when(driverAuthorizationRepository.exists(validId)).thenReturn(true);
    when(driverAuthorizationRepository.isAllowedToCharge(validId)).thenReturn(false);
    ChargingRequest request =
        new ChargingRequest(
            UUID.randomUUID(),
            new AuthorizationRequest(UUID.randomUUID(), new DriverIdentifier(validId)));
    AuthorizationResponse response = authorizationService.processAuthorization(request);
    assertEquals(DriverStatus.Rejected, response.authorizationStatus());
  }
}
