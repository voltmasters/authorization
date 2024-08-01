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
