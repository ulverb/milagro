package com.producer.service;


import com.producer.dto.PersonDto;
import com.producer.kafka.service.PersonKafkaProducer;
import com.producer.service.interfaces.PersonManagement;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("PersonManagementService")
public class PersonManagementService implements PersonManagement {

    private final PersonKafkaProducer personKafkaProducer;

    @Autowired
    public PersonManagementService(PersonKafkaProducer personKafkaProducer) {
        this.personKafkaProducer = personKafkaProducer;
    }
    @Override
    public void sendPersonToKafka(PersonDto person) {
        personKafkaProducer.sendPersonMessage(person);
    }
}