package com.example.KafkaConsumer.config;


import com.example.KafkaConsumer.model.Earthquake;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {
    @Bean
    public ConsumerFactory<String, Earthquake> earthquakeConsumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:29092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Earthquake.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Earthquake> earthquakeKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Earthquake> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(earthquakeConsumerFactory());
        return factory;
    }
}
