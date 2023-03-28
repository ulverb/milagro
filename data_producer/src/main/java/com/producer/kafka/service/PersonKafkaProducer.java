package com.producer.kafka.service;

import com.producer.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonKafkaProducer {

    private final String personProducerTopicName;
    private final KafkaTemplate<String, PersonDto> personKafkaTemplate;

    @Autowired
    public PersonKafkaProducer(KafkaTemplate<String, PersonDto> personKafkaTemplate, @Value("${topic.name.producer}") String personProducerTopicName) {
        this.personKafkaTemplate = personKafkaTemplate;
        this.personProducerTopicName = personProducerTopicName;
    }

    public void sendPersonMessage(PersonDto person) {
        ProducerRecord<String, PersonDto> record = new ProducerRecord<>(personProducerTopicName, person);

        try {
            personKafkaTemplate.send(record);
        }catch(Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
    }
}
