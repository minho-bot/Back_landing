package osteam.backland.domain.person.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class PersonCreateServiceTest {
    private final PersonCreateService personCreateService;
    private final PersonOnlyRepository personOnlyRepository;
    private final PersonOneToOneRepository personOneToOneRepository;
    private final PersonOneToManyRepository personOneToManyRepository;
    private final PhoneOneToOneRepository phoneOneToOneRepository;
    private final PhoneOneToManyRepository phoneOneToManyRepository;

    @Autowired
    public PersonCreateServiceTest(PersonCreateService personCreateService, PersonOnlyRepository personOnlyRepository, PersonOneToOneRepository personOneToOneRepository, PersonOneToManyRepository personOneToManyRepository, PhoneOneToOneRepository phoneOneToOneRepository, PhoneOneToManyRepository phoneOneToManyRepository) {
        this.personCreateService = personCreateService;
        this.personOnlyRepository = personOnlyRepository;
        this.personOneToOneRepository = personOneToOneRepository;
        this.personOneToManyRepository = personOneToManyRepository;
        this.phoneOneToOneRepository = phoneOneToOneRepository;
        this.phoneOneToManyRepository = phoneOneToManyRepository;
    }

    @Test
    @Transactional
    public void testOneToOne() {
        String name = "testman";
        String phone = "01012341234";

        personCreateService.oneToOne(name, phone);

        PersonOneToOne person = personOneToOneRepository.findByName(name).get(0);

        assertEquals(name, person.getName());
        assertEquals(phone, person.getPhoneOneToOne().getPhone());
    }

//    @Test
//    @Transactional
//    public void testOneToMany() {
//        String name = "minho";
//        String phone = "1234567890";
//
//        PersonOneToMany personOneToMany = new PersonOneToMany();
//        personOneToMany.setName(name);
//        personOneToMany.setPhoneOneToMany(new ArrayList<>());
//
//        PhoneOneToMany phoneOneToMany = new PhoneOneToMany();
//        phoneOneToMany.setPhone(phone);
//
//        when(personOneToManyRepository.findByName(name)).thenReturn(Optional.of(personOneToMany));
//        when(phoneOneToManyRepository.findByPhone(phone)).thenReturn(Optional.of(phoneOneToMany));
//        when(personOneToManyRepository.save(any())).thenReturn(personOneToMany);
//
//        PersonDTO result = personCreateService.oneToMany(name, phone);
//
//        assertEquals(name, result.getName());
//        assertEquals(phone, result.getPhone());
//    }
//
//    @Test
//    @Transactional
//    public void testOne() {
//        String name = "minho";
//        String phone = "1234567890";
//
//        PersonOnly personOnly = new PersonOnly();
//        personOnly.setName(name);
//        personOnly.setPhone(phone);
//
//        when(personOnlyRepository.save(any())).thenReturn(personOnly);
//
//        PersonDTO result = personCreateService.one(name, phone);
//
//        assertEquals(name, result.getName());
//        assertEquals(phone, result.getPhone());
//    }
}
