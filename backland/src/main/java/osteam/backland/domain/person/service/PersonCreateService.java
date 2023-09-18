package osteam.backland.domain.person.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.domain.phone.repository.PhoneOneToManyRepository;
import osteam.backland.domain.phone.repository.PhoneOneToOneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonCreateService {

    private final PersonOnlyRepository personOnlyRepository;
    private final PersonOneToOneRepository personOneToOneRepository;
    private final PersonOneToManyRepository personOneToManyRepository;
    private final PhoneOneToOneRepository phoneOneToOneRepository;
    private final PhoneOneToManyRepository phoneOneToManyRepository;

    @Autowired
    public PersonCreateService(
            PersonOnlyRepository personOnlyRepository,
            PersonOneToOneRepository personOneToOneRepository,
            PersonOneToManyRepository personOneToManyRepository,
            PhoneOneToOneRepository phoneOneToOneRepository,
            PhoneOneToManyRepository phoneOneToManyRepository
    ){
        this.personOnlyRepository = personOnlyRepository;
        this.personOneToOneRepository = personOneToOneRepository;
        this.personOneToManyRepository = personOneToManyRepository;
        this.phoneOneToOneRepository = phoneOneToOneRepository;
        this.phoneOneToManyRepository = phoneOneToManyRepository;
    }

    /**
     * Phone과 OneToOne 관계인 person 생성
     */
    @Transactional
    public PersonDTO oneToOne(String name, String phone) {
        PersonOneToOne personOneToOne = new PersonOneToOne();
        personOneToOne.setName(name);

        PhoneOneToOne phoneOneToOne = new PhoneOneToOne();
        phoneOneToOne.setPhone(phone);

        personOneToOne.setPhoneOneToOne(phoneOneToOne);
        phoneOneToOne.setPersonOneToOne(personOneToOne);

        personOneToOneRepository.save(personOneToOne);
        phoneOneToOneRepository.save(phoneOneToOne);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(personOneToOne.getName());
        personDTO.setPhone(phoneOneToOne.getPhone());
        return personDTO;
    }

    /**
     * Phone과 OneToMany 관계인 person 생성
     */
    public PersonDTO oneToMany(String name, String phone) {
        PersonOneToMany person;
        Optional<PersonOneToMany> personExist = personOneToManyRepository.findByName(name);
        if(personExist.isPresent()){
            person = personExist.get();
        }
        else{
            person = new PersonOneToMany();
            person.setName(name);
            person.setPhoneOneToMany(new ArrayList<>());
        }
        PhoneOneToMany phonenumber;
        Optional<PhoneOneToMany> phoneExist = phoneOneToManyRepository.findByPhone(phone);
        if(phoneExist.isPresent()){
            phonenumber = phoneExist.get();
        }
        else{
            phonenumber = new PhoneOneToMany();
            phonenumber.setPhone(phone);
        }
        List<PhoneOneToMany> phones = person.getPhoneOneToMany();
        phones.add(phonenumber);
        phonenumber.setPersonOneToMany(person);
        person.setPhoneOneToMany(phones);
        personOneToManyRepository.save(person);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName());
        personDTO.setPhone(phonenumber.getPhone());

        return personDTO;
    }

    /**
     * person 하나로만 구성되어 있는 생성
     */
    @Transactional
    public PersonDTO one(String name, String phone) {
        PersonOnly personOnly = new PersonOnly();
        personOnly.setName(name);
        personOnly.setPhone(phone);

        personOnlyRepository.save(personOnly);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(personOnly.getName());
        personDTO.setPhone(personOnly.getPhone());

        return personDTO;
    }
}
