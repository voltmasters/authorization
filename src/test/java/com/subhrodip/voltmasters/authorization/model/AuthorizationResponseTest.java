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
