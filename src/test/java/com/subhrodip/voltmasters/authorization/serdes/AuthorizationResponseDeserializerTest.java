package com.subhrodip.voltmasters.authorization.serdes;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhrodip.voltmasters.authorization.model.AuthorizationResponse;
import com.subhrodip.voltmasters.authorization.model.DriverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorizationResponseDeserializerTest {

  private AuthorizationResponseDeserializer deserializer;
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    deserializer = new AuthorizationResponseDeserializer();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void deserializeValidData() throws Exception {
    AuthorizationResponse response = new AuthorizationResponse(DriverStatus.Accepted);
    byte[] data = objectMapper.writeValueAsBytes(response);

    AuthorizationResponse result = deserializer.deserialize("test-topic", data);

    assertNotNull(result);
    assertEquals(response.authorizationStatus(), result.authorizationStatus());
  }

  @Test
  public void deserializeInvalidData() {
    byte[] data = "invalid data".getBytes();

    assertThrows(RuntimeException.class, () -> deserializer.deserialize("test-topic", data));
  }

  @Test
  public void deserializeEmptyData() {
    byte[] data = new byte[0];

    assertThrows(RuntimeException.class, () -> deserializer.deserialize("test-topic", data));
  }
}
