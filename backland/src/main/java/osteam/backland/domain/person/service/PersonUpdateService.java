package osteam.backland.domain.person.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.domain.phone.repository.PhoneOneToOneRepository;

@Service
@Slf4j
public class PersonUpdateService {
    private final PersonOnlyRepository personOnlyRepository;
    private final PersonOneToOneRepository personOneToOneRepository;
    private final PersonOneToManyRepository personOneToManyRepository;
    private final PhoneOneToOneRepository phoneOneToOneRepository;

    @Autowired
    public PersonUpdateService(
            PersonOnlyRepository personOnlyRepository,
            PersonOneToOneRepository personOneToOneRepository,
            PersonOneToManyRepository personOneToManyRepository,
            PhoneOneToOneRepository phoneOneToOneRepository
    ){
        this.personOnlyRepository = personOnlyRepository;
        this.personOneToOneRepository = personOneToOneRepository;
        this.personOneToManyRepository = personOneToManyRepository;
        this.phoneOneToOneRepository = phoneOneToOneRepository;
    }

    @Transactional
    public PersonDTO one(String name, String phone) {
        PersonOnly personOnly = personOnlyRepository.findByPhone(phone).get();
        personOnly.setName(name);
        personOnlyRepository.save(personOnly);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(personOnly.getName());
        personDTO.setPhone(personOnly.getPhone());

        return personDTO;
    }

    @Transactional
    public PersonDTO oneToOne(String name, String phone) {
        PhoneOneToOne phoneOneToOne = phoneOneToOneRepository.findByPhone(phone).get();

        PersonOneToOne personOneToOne = phoneOneToOne.getPersonOneToOne();
        personOneToOne.setName(name);

        personOneToOneRepository.save(personOneToOne);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(personOneToOne.getName());
        personDTO.setPhone(phone);
        return personDTO;
    }
}
