package com.subhrodip.voltmasters.authorization.config;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;

import com.subhrodip.voltmasters.authorization.model.AuthorizationRequest;
import com.subhrodip.voltmasters.authorization.model.AuthorizationResponse;
import com.subhrodip.voltmasters.authorization.serdes.AuthorizationRequestDeserializer;
import com.subhrodip.voltmasters.authorization.serdes.AuthorizationRequestSerializer;
import com.subhrodip.voltmasters.authorization.serdes.AuthorizationResponseDeserializer;
import com.subhrodip.voltmasters.authorization.serdes.AuthorizationResponseSerializer;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConfig {

  @Value(value = "${spring.kafka.bootstrap-servers:localhost:9092}")
  private String bootstrapAddress;

  @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
  KafkaStreamsConfiguration kStreamsConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put(APPLICATION_ID_CONFIG, "authorization-service");
    props.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

    return new KafkaStreamsConfiguration(props);
  }

  @Bean
  public Serde<AuthorizationRequest> authorizationRequestSerde() {
    return Serdes.serdeFrom(
        new AuthorizationRequestSerializer(), new AuthorizationRequestDeserializer());
  }

  @Bean
  public Serde<AuthorizationResponse> authorizationResponseSerde() {
    return Serdes.serdeFrom(
        new AuthorizationResponseSerializer(), new AuthorizationResponseDeserializer());
  }

  @Bean
  NewTopic inputTopic() {
    return TopicBuilder.name(KafkaTopics.CHARGE_AUTHORIZATION_INPUT_TOPIC)
        .partitions(3)
        .replicas(1)
        .build();
  }

  @Bean
  NewTopic outputTopic() {
    return TopicBuilder.name(KafkaTopics.CHARGE_AUTHORIZATION_OUTPUT_TOPIC)
        .partitions(3)
        .replicas(1)
        .build();
  }
}
