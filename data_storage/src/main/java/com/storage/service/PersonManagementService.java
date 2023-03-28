package com.storage.service;

import com.storage.dal.mapper.PersonMapper;
import com.storage.dto.PersonDto;
import com.storage.dal.repository.PersonRepository;
import com.storage.service.interfaces.PersonManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service("PersonManagementService")
public class PersonManagementService implements PersonManagement {

    private final PersonRepository personRepository;

    @Autowired
    public PersonManagementService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void savePersonToDB(PersonDto personDto) {

        personRepository.save(PersonMapper.convertPersonDtoToEntity(personDto));
    }
}