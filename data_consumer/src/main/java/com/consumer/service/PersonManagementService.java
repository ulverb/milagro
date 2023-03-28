package com.consumer.service;


import com.consumer.dto.PersonDto;
import com.consumer.service.interfaces.PersonManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PersonManagementService implements PersonManagement {

    private final String resourceUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public PersonManagementService(@Qualifier("restTemplate") RestTemplate restTemplate,
                                   @Value("${person.state.url}") String resourceUrl) {
        this.restTemplate = restTemplate;
        this.resourceUrl = resourceUrl;
    }

    @Override
    public PersonDto sendPersonToStorage(PersonDto personDto) {

        HttpEntity<PersonDto> request = new HttpEntity<>(personDto);

        ResponseEntity<PersonDto> response = restTemplate.postForEntity(resourceUrl, request, PersonDto.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("Failed to store person: ", response.getStatusCodeValue());
            throw new HttpClientErrorException(response.getStatusCode());
        }

        return response.getBody();
    }
}
