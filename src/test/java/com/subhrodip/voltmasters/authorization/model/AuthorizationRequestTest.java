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
