package osteam.backland.domain.person.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.domain.phone.repository.PhoneOneToOneRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PersonUpdateServiceTest {
    @Autowired
    private PersonOnlyRepository personOnlyRepository;
    @Autowired
    private PersonOneToOneRepository personOneToOneRepository;

    @Autowired
    private PhoneOneToOneRepository phoneOneToOneRepository;
    @Autowired
    private PersonUpdateService personUpdateService;

    @Test
    @Transactional
    public void testUpdatePersonOnly() {
        String name = "Alice";
        String changedName = "Bob";
        String phone = "01012341234";

        PersonOnly personOnly = new PersonOnly();
        personOnly.setPhone(phone);
        personOnlyRepository.save(personOnly);

        PersonDTO result = personUpdateService.one(changedName, phone);

        assertEquals(changedName, result.getName());
        assertEquals(phone, result.getPhone());
    }

    @Test
    @Transactional
    public void testUpdatePersonOneToOne() {
        String name = "Alice";
        String changedName = "Bob";
        String phone = "01012341234";

        PhoneOneToOne phoneOneToOne = new PhoneOneToOne();
        PersonOneToOne personOneToOne = new PersonOneToOne();
        phoneOneToOne.setPhone(phone);
        phoneOneToOne.setPersonOneToOne(personOneToOne);
        personOneToOne.setName(name);
        personOneToOne.setPhoneOneToOne(phoneOneToOne);
        personOneToOneRepository.save(personOneToOne);

        PersonDTO result = personUpdateService.oneToOne(changedName, phone);

        assertEquals(changedName, result.getName());
        assertEquals(phone, result.getPhone());
    }
}
