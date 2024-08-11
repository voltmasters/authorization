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

package com.subhrodip.voltmasters.authorization.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import org.junit.jupiter.api.Test;

public class AuthorizationRequestTest {

  @Test
  void testAuthorizationRequestCreation() {
    UUID stationUuid = UUID.randomUUID();
    DriverIdentifier driverIdentifier = new DriverIdentifier(UUID.randomUUID().toString());
    AuthorizationRequest request = new AuthorizationRequest(stationUuid, driverIdentifier);

    assertNotNull(request);
    assertEquals(stationUuid, request.stationUuid());
    assertEquals(driverIdentifier, request.driverIdentifier());
  }

  @Test
  void testAuthorizationRequestWithNullValues() {
    AuthorizationRequest request = new AuthorizationRequest(null, null);

    assertNotNull(request);
    assertNull(request.stationUuid());
    assertNull(request.driverIdentifier());
  }

  @Test
  void testAuthorizationRequestWithNullDriverIdentifier() {
    UUID stationUuid = UUID.randomUUID();
    AuthorizationRequest request = new AuthorizationRequest(stationUuid, null);

    assertNotNull(request);
    assertEquals(stationUuid, request.stationUuid());
    assertNull(request.driverIdentifier());
  }

  @Test
  void testAuthorizationRequestWithNullStationUuid() {
    DriverIdentifier driverIdentifier = new DriverIdentifier(UUID.randomUUID().toString());
    AuthorizationRequest request = new AuthorizationRequest(null, driverIdentifier);

    assertNotNull(request);
    assertNull(request.stationUuid());
    assertEquals(driverIdentifier, request.driverIdentifier());
  }
}
