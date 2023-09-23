package osteam.backland.domain.person.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GraphqlPersonSearchService {

    private final PersonOnlyRepository personOnlyRepository;

    public PersonOnly getPersonByPhone(String phone){
        return personOnlyRepository.findByPhone(phone)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<PersonOnly> getPeopleByName(String name){
        return personOnlyRepository.findByName(name);
    }

    public List<PersonOnly> getAllPeople(){
        return personOnlyRepository.findAll();
    }
}
