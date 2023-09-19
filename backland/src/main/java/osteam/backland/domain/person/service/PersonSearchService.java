package osteam.backland.domain.person.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.exception.PersonNotFoundException;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonSearchService {
    private final PersonOnlyRepository personOnlyRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<PersonDTO> getAllPeople(){
        List<PersonOnly> all = personOnlyRepository.findAll();
        if(all.isEmpty()){
            throw new PersonNotFoundException("people");
        }
        return mapToDTOList(all);
    }

    @Transactional
    public boolean isOneExist(String phone){
        Optional<PersonOnly> existing = personOnlyRepository.findByPhone(phone);
        return existing.isPresent();
    }
    
    @Transactional
    public List<PersonDTO> getPeopleByName(String name){
        List<PersonOnly> people = personOnlyRepository.findByName(name);
        if(people.isEmpty()){
            throw new PersonNotFoundException(name);
        }
        return mapToDTOList(people);
    }

    @Transactional
    public PersonDTO getPersonByPhone(String phone){
        Optional<PersonOnly> existing = personOnlyRepository.findByPhone(phone);
        if(existing.isPresent()){
            PersonOnly person = existing.get();
            return modelMapper.map(person, PersonDTO.class);
        }
        else throw new PersonNotFoundException(phone);
    }

    private List<PersonDTO> mapToDTOList(List<PersonOnly> people){
        return people.stream()
                .map(personOnly -> modelMapper.map(personOnly, PersonDTO.class))
                .collect(Collectors.toList());
    }
}
