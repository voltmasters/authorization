package com.subhrodip.voltmasters.authorization.serdes;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhrodip.voltmasters.authorization.model.AuthorizationRequest;
import com.subhrodip.voltmasters.authorization.model.DriverIdentifier;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorizationRequestDeserializerTest {

  private AuthorizationRequestDeserializer deserializer;
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    deserializer = new AuthorizationRequestDeserializer();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void deserializeValidData() throws Exception {
    AuthorizationRequest request =
        new AuthorizationRequest(
            UUID.randomUUID(), new DriverIdentifier(UUID.randomUUID().toString()));
    byte[] data = objectMapper.writeValueAsBytes(request);

    AuthorizationRequest result = deserializer.deserialize("test-topic", data);

    assertNotNull(result);
    assertEquals(request.stationUuid(), result.stationUuid());
    assertEquals(request.driverIdentifier(), result.driverIdentifier());
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
