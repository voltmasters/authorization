package com.subhrodip.voltmasters.authorization.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subhrodip.voltmasters.authorization.model.ChargingRequest;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

public class ChargingRequestDeserializer implements Deserializer<ChargingRequest> {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {}

  @Override
  public ChargingRequest deserialize(String topic, byte[] data) {
    try {
      return objectMapper.readValue(data, ChargingRequest.class);
    } catch (Exception e) {
      throw new RuntimeException("Error deserializing ChargingRequest", e);
    }
  }

  @Override
  public void close() {}
}
