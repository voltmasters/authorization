package com.subhrodip.voltmasters.authorization.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhrodip.voltmasters.authorization.model.AuthorizationRequest;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

public class AuthorizationRequestDeserializer implements Deserializer<AuthorizationRequest> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {}

  @Override
  public AuthorizationRequest deserialize(String topic, byte[] data) {
    try {
      return objectMapper.readValue(data, AuthorizationRequest.class);
    } catch (Exception e) {
      throw new RuntimeException("Error deserializing AuthorizationRequest", e);
    }
  }

  @Override
  public void close() {}
}
