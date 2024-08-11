package com.subhrodip.voltmasters.authorization.serdes;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhrodip.voltmasters.authorization.model.AuthorizationRequest;
import com.subhrodip.voltmasters.authorization.model.ChargingRequest;
import com.subhrodip.voltmasters.authorization.model.DriverIdentifier;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChargingRequestDeserializerTest {

  private ChargingRequestDeserializer deserializer;
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    deserializer = new ChargingRequestDeserializer();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void deserializeValidData() throws Exception {
    ChargingRequest request =
        new ChargingRequest(
            UUID.randomUUID(),
            new AuthorizationRequest(
                UUID.randomUUID(), new DriverIdentifier(UUID.randomUUID().toString())));
    byte[] data = objectMapper.writeValueAsBytes(request);

    ChargingRequest result = deserializer.deserialize("test-topic", data);

    assertNotNull(result);
    assertEquals(request.requestId(), result.requestId());
    assertEquals(
        request.authorizationRequest().driverIdentifier().id(),
        result.authorizationRequest().driverIdentifier().id());
    assertEquals(
        request.authorizationRequest().stationUuid(), result.authorizationRequest().stationUuid());
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
