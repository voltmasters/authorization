package com.subhrodip.voltmasters.authorization.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;
import org.junit.jupiter.api.Test;

public class ChargingRequestTest {

  @Test
  public void testChargingRequestCreation() {
    UUID requestId = UUID.randomUUID();
    AuthorizationRequest authorizationRequest =
        new AuthorizationRequest(
            UUID.randomUUID(), new DriverIdentifier(UUID.randomUUID().toString()));
    ChargingRequest chargingRequest = new ChargingRequest(requestId, authorizationRequest);

    assertNotNull(chargingRequest);
    assertEquals(requestId, chargingRequest.requestId());
    assertEquals(authorizationRequest, chargingRequest.authorizationRequest());
  }

  @Test
  public void testChargingRequestWithNullValues() {
    ChargingRequest chargingRequest = new ChargingRequest(null, null);

    assertNotNull(chargingRequest);
    assertNull(chargingRequest.requestId());
    assertNull(chargingRequest.authorizationRequest());
  }

  @Test
  public void testChargingRequestWithNullAuthorizationRequest() {
    UUID requestId = UUID.randomUUID();
    ChargingRequest chargingRequest = new ChargingRequest(requestId, null);

    assertNotNull(chargingRequest);
    assertEquals(requestId, chargingRequest.requestId());
    assertNull(chargingRequest.authorizationRequest());
  }

  @Test
  public void testChargingRequestWithNullRequestId() {
    AuthorizationRequest authorizationRequest =
        new AuthorizationRequest(
            UUID.randomUUID(), new DriverIdentifier(UUID.randomUUID().toString()));
    ChargingRequest chargingRequest = new ChargingRequest(null, authorizationRequest);

    assertNotNull(chargingRequest);
    assertNull(chargingRequest.requestId());
    assertEquals(authorizationRequest, chargingRequest.authorizationRequest());
  }
}
