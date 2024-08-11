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
import com.subhrodip.voltmasters.authorization.model.AuthorizationResponse;
import com.subhrodip.voltmasters.authorization.model.DriverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorizationResponseSerializerTest {

  private AuthorizationResponseSerializer serializer;
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    serializer = new AuthorizationResponseSerializer();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void serializeValidData() throws Exception {
    AuthorizationResponse response = new AuthorizationResponse(DriverStatus.Accepted);
    byte[] data = serializer.serialize("test-topic", response);

    AuthorizationResponse result = objectMapper.readValue(data, AuthorizationResponse.class);

    assertNotNull(result);
    assertEquals(response.authorizationStatus(), result.authorizationStatus());
  }
}
