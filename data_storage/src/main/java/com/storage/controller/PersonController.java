package com.storage.controller;

import com.storage.dto.PersonDto;
import com.storage.dto.ResponseDataDto;
import com.storage.errors.BadRequestException;
import com.storage.service.interfaces.PersonManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/upload")
public class PersonController {


    private final PersonManagement personManagement;

    public PersonController(PersonManagement personManagement) {
        this.personManagement = personManagement;
    }


    @RequestMapping(value="/person", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTopPlayers(@RequestBody PersonDto input){

        inputValidation(input);

        personManagement.savePersonToDB(input);
;
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .body(ResponseDataDto.builder().message("Person saved !")
                        .build());
    }

    private void inputValidation(PersonDto input){

        if(input.getId().length() !=7 || input.getAge() < 0 || input.getFullName().isBlank()){
            throw new BadRequestException("Person input values are invalid!");
        }
    }
}
