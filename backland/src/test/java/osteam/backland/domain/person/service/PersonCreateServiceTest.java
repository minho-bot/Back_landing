package osteam.backland.domain.person.service;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonCreateServiceTest {
    @InjectMocks
    private PersonCreateService personCreateService;
    @Mock
    private PersonOnlyRepository personOnlyRepository;
    @Mock
    private PersonOneToOneRepository personOneToOneRepository;
    @Mock
    private PersonOneToManyRepository personOneToManyRepository;
    @Mock
    private PhoneOneToOneRepository phoneOneToOneRepository;

    @Mock
    private PhoneOneToManyRepository phoneOneToManyRepository;

    public PersonCreateServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    public void testOneToOne() {
        String name = "minho";
        String phone = "1234567890";

        PersonOneToOne personOneToOne = new PersonOneToOne();
        personOneToOne.setName(name);

        PhoneOneToOne phoneOneToOne = new PhoneOneToOne();
        phoneOneToOne.setPhone(phone);

        when(personOneToOneRepository.save(any())).thenReturn(personOneToOne);
        when(phoneOneToOneRepository.save(any())).thenReturn(phoneOneToOne);

        PersonDTO result = personCreateService.oneToOne(name, phone);

        assertEquals(name, result.getName());
        assertEquals(phone, result.getPhone());
    }

    @Test
    @Transactional
    public void testOneToMany() {
        String name = "minho";
        String phone = "1234567890";

        PersonOneToMany personOneToMany = new PersonOneToMany();
        personOneToMany.setName(name);
        personOneToMany.setPhoneOneToMany(new ArrayList<>());

        PhoneOneToMany phoneOneToMany = new PhoneOneToMany();
        phoneOneToMany.setPhone(phone);

        when(personOneToManyRepository.findByName(name)).thenReturn(Optional.of(personOneToMany));
        when(phoneOneToManyRepository.findByPhone(phone)).thenReturn(Optional.of(phoneOneToMany));
        when(personOneToManyRepository.save(any())).thenReturn(personOneToMany);

        PersonDTO result = personCreateService.oneToMany(name, phone);

        assertEquals(name, result.getName());
        assertEquals(phone, result.getPhone());
    }

    @Test
    @Transactional
    public void testOne() {
        String name = "minho";
        String phone = "1234567890";

        PersonOnly personOnly = new PersonOnly();
        personOnly.setName(name);
        personOnly.setPhone(phone);

        when(personOnlyRepository.save(any())).thenReturn(personOnly);

        PersonDTO result = personCreateService.one(name, phone);

        assertEquals(name, result.getName());
        assertEquals(phone, result.getPhone());
    }
}
