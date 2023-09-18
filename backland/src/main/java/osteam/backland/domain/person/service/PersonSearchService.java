package osteam.backland.domain.person.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonSearchService {
    private final PersonOnlyRepository personOnlyRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonSearchService(
            PersonOnlyRepository personOnlyRepository,
            ModelMapper modelMapper
    ){
        this.personOnlyRepository = personOnlyRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public List<PersonDTO> getAllPeople(){
        List<PersonOnly> all = personOnlyRepository.findAll();
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
        return mapToDTOList(people);
    }

    @Transactional
    public PersonDTO getPersonByPhone(String phone){
        Optional<PersonOnly> existing = personOnlyRepository.findByPhone(phone);
        if(existing.isPresent()){
            PersonOnly person = existing.get();
            return modelMapper.map(person, PersonDTO.class);
        }
        else return null;
    }

    private List<PersonDTO> mapToDTOList(List<PersonOnly> people){
        return people.stream()
                .map(personOnly -> modelMapper.map(personOnly, PersonDTO.class))
                .collect(Collectors.toList());
    }
}
