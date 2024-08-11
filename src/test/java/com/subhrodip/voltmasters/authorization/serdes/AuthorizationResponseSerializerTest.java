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
