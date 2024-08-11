/*
 * authorization - Authorization Service for authenticating requests from Charging Stations
 * Copyright © 2024 Subhrodip Mohanta (contact@subhrodip.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
