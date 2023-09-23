package osteam.backland.domain.person.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.service.GraphqlPersonSearchService;
import osteam.backland.domain.person.service.PersonSearchService;

import java.util.List;

@Slf4j
@Controller
public class GraphqlPersonController {
    private final GraphqlPersonSearchService personSearchService;

    @Autowired
    GraphqlPersonController(GraphqlPersonSearchService personSearchService){
        this.personSearchService = personSearchService;
    }

    @QueryMapping
    public List<PersonOnly> getPeople(){
        return personSearchService.getAllPeople();
    }

    @QueryMapping
    public List<PersonOnly> getPeopleByName(@Argument String name){
        return personSearchService.getPeopleByName(name);
    }

    @QueryMapping
    public PersonOnly getPersonByPhone(@Argument String phone){
        return personSearchService.getPersonByPhone(phone);
    }
}
