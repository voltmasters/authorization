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

package com.subhrodip.voltmasters.authorization.serdes;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhrodip.voltmasters.authorization.model.AuthorizationRequest;
import com.subhrodip.voltmasters.authorization.model.ChargingRequest;
import com.subhrodip.voltmasters.authorization.model.DriverIdentifier;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChargingRequestSerializerTest {

  private ChargingRequestSerializer serializer;
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    serializer = new ChargingRequestSerializer();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void serializeValidData() throws Exception {
    ChargingRequest request =
        new ChargingRequest(
            UUID.randomUUID(),
            new AuthorizationRequest(
                UUID.randomUUID(), new DriverIdentifier(UUID.randomUUID().toString())));
    byte[] data = serializer.serialize("test-topic", request);

    ChargingRequest result = objectMapper.readValue(data, ChargingRequest.class);

    assertNotNull(result);
    assertEquals(request.requestId(), result.requestId());
    assertEquals(
        request.authorizationRequest().stationUuid(), result.authorizationRequest().stationUuid());
    assertEquals(
        request.authorizationRequest().driverIdentifier().id(),
        result.authorizationRequest().driverIdentifier().id());
  }

  @Test
  public void serializeEmptyRequestId() throws Exception {
    ChargingRequest request =
        new ChargingRequest(
            null,
            new AuthorizationRequest(
                UUID.randomUUID(), new DriverIdentifier(UUID.randomUUID().toString())));
    byte[] data = serializer.serialize("test-topic", request);

    ChargingRequest result = objectMapper.readValue(data, ChargingRequest.class);

    assertNotNull(result);
    assertNull(result.requestId());
    assertEquals(
        request.authorizationRequest().stationUuid(), result.authorizationRequest().stationUuid());
    assertEquals(
        request.authorizationRequest().driverIdentifier().id(),
        result.authorizationRequest().driverIdentifier().id());
  }

  @Test
  public void serializeEmptyAuthorizationRequest() throws Exception {
    ChargingRequest request = new ChargingRequest(UUID.randomUUID(), null);
    byte[] data = serializer.serialize("test-topic", request);

    ChargingRequest result = objectMapper.readValue(data, ChargingRequest.class);

    assertNotNull(result);
    assertEquals(request.requestId(), result.requestId());
    assertNull(result.authorizationRequest());
  }
}
