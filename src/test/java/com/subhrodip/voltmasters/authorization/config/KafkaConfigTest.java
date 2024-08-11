package com.subhrodip.voltmasters.authorization.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.subhrodip.voltmasters.authorization.model.AuthorizationRequest;
import com.subhrodip.voltmasters.authorization.model.AuthorizationResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@SpringBootTest
public class KafkaConfigTest {

  @Autowired private KafkaConfig kafkaConfig;

  @Autowired private KafkaStreamsConfiguration kafkaStreamsConfiguration;

  @Autowired private Serde<AuthorizationRequest> authorizationRequestSerde;

  @Autowired private Serde<AuthorizationResponse> authorizationResponseSerde;

  @Test
  void testKafkaStreamsConfiguration() {
    assertNotNull(kafkaStreamsConfiguration);
    assertEquals(
        "authorization-service",
        kafkaStreamsConfiguration.asProperties().get(StreamsConfig.APPLICATION_ID_CONFIG));
    assertEquals(
        "localhost:9092",
        kafkaStreamsConfiguration.asProperties().get(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG));
  }

  @Test
  void testInputTopic() {
    NewTopic inputTopic = kafkaConfig.inputTopic();
    assertNotNull(inputTopic);
    assertEquals("charge-authorization-request", inputTopic.name());
    assertEquals(3, inputTopic.numPartitions());
    assertEquals(1, inputTopic.replicationFactor());
  }

  @Test
  void testOutputTopic() {
    NewTopic outputTopic = kafkaConfig.outputTopic();
    assertNotNull(outputTopic);
    assertEquals("charge-authorization-response", outputTopic.name());
    assertEquals(3, outputTopic.numPartitions());
    assertEquals(1, outputTopic.replicationFactor());
  }

  @Test
  void testAuthorizationRequestSerde() {
    assertNotNull(authorizationRequestSerde);
  }

  @Test
  void testAuthorizationResponseSerde() {
    assertNotNull(authorizationResponseSerde);
  }
}
