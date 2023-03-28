package com.consumer.service.interfaces;

import com.consumer.dto.PersonDto;

public interface PersonManagement {
    PersonDto sendPersonToStorage(PersonDto personDto);
}
