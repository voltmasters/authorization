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
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class StationAuthorizationRepository {

  private Map<UUID, Boolean> validStations = new HashMap<>();

  public StationAuthorizationRepository() {
    // Preload some identifiers
    validStations.put(UUID.fromString("59419c4f-b8b8-4def-b460-f8c11ae426ea"), true); // Activated
    validStations.put(
        UUID.fromString("80ba0cee-e458-49e3-8fed-ee2524a4b943"), false); // Not activated
  }

  public boolean exists(UUID identifier) {
    return validStations.containsKey(identifier);
  }

  public boolean isAllowedToCharge(UUID identifier) {
    return validStations.getOrDefault(identifier, false);
  }

  public void addIdentifier(UUID identifier, boolean allowedToCharge) {
    validStations.put(identifier, allowedToCharge);
  }

  public void removeIdentifier(UUID identifier) {
    validStations.remove(identifier);
  }

  public void updateIdentifier(UUID identifier, boolean allowedToCharge) {
    validStations.put(identifier, allowedToCharge);
  }
}
