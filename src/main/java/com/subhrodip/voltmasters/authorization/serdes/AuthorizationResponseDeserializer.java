package com.subhrodip.voltmasters.authorization.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhrodip.voltmasters.authorization.model.AuthorizationResponse;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

public class AuthorizationResponseDeserializer implements Deserializer<AuthorizationResponse> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {}

  @Override
  public AuthorizationResponse deserialize(String topic, byte[] data) {
    try {
      return objectMapper.readValue(data, AuthorizationResponse.class);
    } catch (Exception e) {
      throw new RuntimeException("Error deserializing AuthorizationResponse", e);
    }
  }

  @Override
  public void close() {}
}
