package com.producer.service.interfaces;

import com.producer.dto.PersonDto;


public interface PersonManagement {
    void sendPersonToKafka(PersonDto person);
}
