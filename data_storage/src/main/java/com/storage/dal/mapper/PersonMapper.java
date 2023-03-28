package com.storage.dal.mapper;

import com.storage.dal.entity.PersonEntity;
import com.storage.dto.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public static PersonDto convertPersonEntityToDto(PersonEntity personEntity){
        return PersonDto.builder()
                .id(personEntity.getId())
                .fullName(personEntity.getFullName())
                .age(personEntity.getAge())
                .build();
    }

    public static PersonEntity convertPersonDtoToEntity(PersonDto personDto){
        return PersonEntity.builder()
                .id(personDto.getId())
                .fullName(personDto.getFullName())
                .age(personDto.getAge())
                .build();
    }
}
