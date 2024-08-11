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
import com.subhrodip.voltmasters.authorization.model.DriverIdentifier;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorizationRequestDeserializerTest {

  private AuthorizationRequestDeserializer deserializer;
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    deserializer = new AuthorizationRequestDeserializer();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void deserializeValidData() throws Exception {
    AuthorizationRequest request =
        new AuthorizationRequest(
            UUID.randomUUID(), new DriverIdentifier(UUID.randomUUID().toString()));
    byte[] data = objectMapper.writeValueAsBytes(request);

    AuthorizationRequest result = deserializer.deserialize("test-topic", data);

    assertNotNull(result);
    assertEquals(request.stationUuid(), result.stationUuid());
    assertEquals(request.driverIdentifier(), result.driverIdentifier());
  }

  @Test
  public void deserializeInvalidData() {
    byte[] data = "invalid data".getBytes();

    assertThrows(RuntimeException.class, () -> deserializer.deserialize("test-topic", data));
  }

  @Test
  public void deserializeEmptyData() {
    byte[] data = new byte[0];

    assertThrows(RuntimeException.class, () -> deserializer.deserialize("test-topic", data));
  }
}
