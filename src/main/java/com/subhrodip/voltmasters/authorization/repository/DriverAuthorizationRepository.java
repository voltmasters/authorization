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
