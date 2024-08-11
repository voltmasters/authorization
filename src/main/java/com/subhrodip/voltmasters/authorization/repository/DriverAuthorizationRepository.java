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

package com.subhrodip.voltmasters.authorization.repository;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class DriverAuthorizationRepository {

  private Map<String, Boolean> validDrivers = new HashMap<>();

  public DriverAuthorizationRepository() {
    // Preload some identifiers
    validDrivers.put("257a19ed-4bff-4e32-b692-7da2e3860ef3", true);
    validDrivers.put("0fe9dce6-fbae-4da8-be90-392a53c0860b", false);
  }

  public boolean exists(String identifier) {
    return validDrivers.containsKey(identifier);
  }

  public boolean isAllowedToCharge(String identifier) {
    return validDrivers.getOrDefault(identifier, false);
  }

  public void addIdentifier(String identifier, boolean allowedToCharge) {
    validDrivers.put(identifier, allowedToCharge);
  }

  public void removeIdentifier(String identifier) {
    validDrivers.remove(identifier);
  }

  public void updateIdentifier(String identifier, boolean allowedToCharge) {
    validDrivers.put(identifier, allowedToCharge);
  }
}
