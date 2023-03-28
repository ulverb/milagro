package com.producer.controller;


import com.producer.dto.ResponseDataDto;
import com.producer.errors.BadRequestException;
import com.producer.dto.PersonDto;
import com.producer.service.interfaces.PersonManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        personManagement.sendPersonToKafka(input);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE))
                .body(ResponseDataDto.builder().message("Person received !")
                        .build());
    }

    private void inputValidation(PersonDto input){

        if(input.getId().length() !=7 || input.getAge() < 0 || input.getFullName().isBlank()){
            throw new BadRequestException("Person input values are invalid!");
        }
    }
}
