package com.subhrodip.voltmasters.authorization.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhrodip.voltmasters.authorization.model.AuthorizationResponse;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;

public class AuthorizationResponseSerializer implements Serializer<AuthorizationResponse> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {}

  @Override
  public byte[] serialize(String topic, AuthorizationResponse data) {
    try {
      return objectMapper.writeValueAsBytes(data);
    } catch (Exception e) {
      throw new RuntimeException("Error serializing AuthorizationResponse", e);
    }
  }

  @Override
  public void close() {}
}
