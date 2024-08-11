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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class AuthorizationResponseTest {

  @Test
  public void testAuthorizationResponseCreation() {
    AuthorizationResponse response = new AuthorizationResponse(DriverStatus.Accepted);

    assertNotNull(response);
    assertEquals(DriverStatus.Accepted, response.authorizationStatus());
  }

  @Test
  public void testAuthorizationResponseWithNullStatus() {
    AuthorizationResponse response = new AuthorizationResponse(null);

    assertNotNull(response);
    assertEquals(null, response.authorizationStatus());
  }
}
