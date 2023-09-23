package osteam.backland.domain.person.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.exception.PersonNotFoundException;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonSearchServiceTest {
    @Autowired
    private PersonOnlyRepository personOnlyRepository;
    @Autowired
    private PersonSearchService personSearchService;

    @Test
    @Transactional
    public void testGetAllPeople() {
        personOnlyRepository.deleteAll();
        PersonOnly person1 = new PersonOnly();
        person1.setName("Alice");
        person1.setPhone("01012341234");
        personOnlyRepository.save(person1);

        PersonOnly person2 = new PersonOnly();
        person2.setName("Bob");
        person2.setPhone("01043214321");
        personOnlyRepository.save(person2);

        List<PersonDTO> result = personSearchService.getAllPeople();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("Bob", result.get(1).getName());
    }

    @Test
    @Transactional
    public void testGetAllPeopleEmpty() {
        personOnlyRepository.deleteAll();

        assertThrows(PersonNotFoundException.class, () -> {
            personSearchService.getAllPeople();
        });
    }

    @Test
    @Transactional
    public void testIsOneExist() {
        personOnlyRepository.deleteAll();
        PersonOnly person = new PersonOnly();
        person.setName("Alice");
        person.setPhone("01012341234");
        personOnlyRepository.save(person);

        boolean result = personSearchService.isOneExist("01012341234");
        assertTrue(result);

        result = personSearchService.isOneExist("01099999999");
        assertFalse(result);
    }

    @Test
    @Transactional
    public void testGetPeopleByName() {
        personOnlyRepository.deleteAll();
        PersonOnly person1 = new PersonOnly();
        person1.setName("Alice");
        person1.setPhone("01012341234");
        personOnlyRepository.save(person1);

        PersonOnly person2 = new PersonOnly();
        person2.setName("Bob");
        person2.setPhone("01043214321");
        personOnlyRepository.save(person2);

        List<PersonDTO> result = personSearchService.getPeopleByName("Alice");
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());

        assertThrows(PersonNotFoundException.class, () -> {
            personSearchService.getPeopleByName("Eve");
        });
    }

    @Test
    @Transactional
    public void testGetPersonByPhone() {
        personOnlyRepository.deleteAll();
        PersonOnly person = new PersonOnly();
        person.setName("Alice");
        person.setPhone("01012341234");
        personOnlyRepository.save(person);

        PersonDTO result = personSearchService.getPersonByPhone("01012341234");
        assertNotNull(result);
        assertEquals("Alice", result.getName());

        assertThrows(PersonNotFoundException.class, () -> {
            personSearchService.getPersonByPhone("01099999999");
        });
    }
}