package com.consumer.kafka.service;


import com.consumer.dto.PersonDto;
import com.consumer.service.PersonManagementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j

@Service
public class PersonConsumerService {

    private final PersonManagementService personManagementService;

    @Autowired
    public PersonConsumerService(PersonManagementService personManagementService) {
        this.personManagementService = personManagementService;
    }

    @KafkaListener(topics = "${topic.name.consumer}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "personKafkaListenerContainerFactory",
            concurrency = "3")
    public void personConsumer(ConsumerRecord<String, PersonDto> record){

        personManagementService.sendPersonToStorage(record.value());

    }
}
