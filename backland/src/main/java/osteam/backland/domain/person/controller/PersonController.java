package osteam.backland.domain.person.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import osteam.backland.domain.person.controller.request.PersonCreateRequest;
import osteam.backland.domain.person.controller.response.PersonResponse;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.service.PersonCreateService;
import osteam.backland.domain.person.service.PersonSearchService;
import osteam.backland.domain.person.service.PersonUpdateService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PersonController
 * 등록 수정 검색 구현
 * <p>
 * 등록 - 입력된 이름과 전화번호를 personOnly, personOneToOne, personOneToMany에 저장
 * 수정 - 이미 등록된 전화번호가 입력될 경우 해당 전화번호의 소유주 이름을 변경
 * 검색 - 전체 출력, 이름으로 검색, 전화번호로 검색 구현, 검색은 전부 OneToMany 테이블로 진행.
 */
@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonCreateService personCreateService;
    private PersonUpdateService personUpdateService;
    private PersonSearchService personSearchService;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonController(
            PersonCreateService personCreateSErvice,
            PersonUpdateService personUpdateService,
            PersonSearchService personSearchService,
            ModelMapper modelMapper
    ) {
        this.personCreateService = personCreateSErvice;
        this.personUpdateService = personUpdateService;
        this.personSearchService = personSearchService;
        this.modelMapper = modelMapper;
    }

    /**
     * 등록 기능
     * personRequest를 service에 그대로 넘겨주지 말 것.
     *
     * @param personCreateRequest
     * @return 성공 시 이름 반환
     */
    @PostMapping
    @Operation(summary = "Create a new person", description = "Create a new person with the provided information.")
    public ResponseEntity<PersonResponse> person(@Valid @RequestBody PersonCreateRequest personCreateRequest) {
        String name = personCreateRequest.getName();
        String phone = personCreateRequest.getPhone();
        PersonDTO one;

        boolean isDuplicate = personSearchService.isOneExist(phone);
        if(isDuplicate){
            one = personUpdateService.one(name, phone);
            personUpdateService.oneToOne(name, phone);
            System.out.println("updated");
        }
        else{
            one = personCreateService.one(name, phone);
            personCreateService.oneToOne(name, phone);
            System.out.println("created");
        }

        personCreateService.oneToMany(name, phone);

        PersonResponse response = modelMapper.map(one, PersonResponse.class);
        return ResponseEntity.ok(response);
    }

    /**
     * 전체 검색 기능
     */
    @GetMapping
    @Operation(summary = "Get all people", description = "Retrieve a list of all people.")
    public ResponseEntity<List<PersonResponse>> getPeople() {
        List<PersonDTO> allPeople = personSearchService.getAllPeople();
        if (allPeople.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PersonResponse> response = allPeople.stream()
                .map(personDTO -> modelMapper.map(personDTO, PersonResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * 이름으로 검색
     *
     * @param name
     */
    @GetMapping("/name")
    @Operation(summary = "Get people by name", description = "Retrieve a list of people by their name.")
    public ResponseEntity<List<PersonResponse>> getPeopleByName(String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<PersonDTO> people = personSearchService.getPeopleByName(name);
        if (people.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PersonResponse> response = people.stream()
                .map(personDTO -> modelMapper.map(personDTO, PersonResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * 번호로 검색
     *
     * @param phone
     */
    @GetMapping("/phone")
    @Operation(summary = "Get person by phone number", description = "Retrieve a person by their phone number.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found and retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<PersonResponse> getPersonByPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        PersonDTO person = personSearchService.getPersonByPhone(phone);
        if(person == null){
            return ResponseEntity.notFound().build();
        }
        else{
            PersonResponse response = modelMapper.map(person, PersonResponse.class);
            return ResponseEntity.ok(response);

        }
    }
}
