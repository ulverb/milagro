package com.consumer.kafka.config;

import java.util.HashMap;
import java.util.Map;

import com.consumer.dto.PersonDto;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.MicrometerConsumerListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;
    @Value(value="${spring.kafka.consumer.group-id}")
    private String groupId;
    @Autowired
    MeterRegistry registry;

    @Bean
    public ConsumerFactory<String, PersonDto> personConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.consumer.dto.PersonDto");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS,false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        ConsumerFactory<String, PersonDto> consumer = new DefaultKafkaConsumerFactory<>(props);
        consumer.addListener(new MicrometerConsumerListener<>(registry));
        return consumer;

    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PersonDto> personKafkaListenerContainerFactory(ConsumerFactory<String, PersonDto> personDtoConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, PersonDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(personDtoConsumerFactory);
        return factory;
    }
}