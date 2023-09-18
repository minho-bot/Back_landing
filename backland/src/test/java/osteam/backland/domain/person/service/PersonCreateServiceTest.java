package osteam.backland.domain.person.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.repository.PersonOneToManyRepository;
import osteam.backland.domain.person.repository.PersonOneToOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.repository.PhoneOneToManyRepository;
import osteam.backland.domain.phone.repository.PhoneOneToOneRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String name = "testManA";
        String phone = "01012341234";

        personCreateService.oneToOne(name, phone);

        PersonOneToOne fromDB = personOneToOneRepository.findByName(name).get(0);

        assertEquals(name, fromDB.getName());
        assertEquals(phone, fromDB.getPhoneOneToOne().getPhone());
    }

    @Test
    @Transactional
    public void testOneToMany() {
        String name = "testManB";
        String phone = "01011223344";

        personCreateService.oneToMany(name, phone);
        PhoneOneToMany fromDB = phoneOneToManyRepository.findByPhone(phone).get();

        assertEquals(name, fromDB.getPersonOneToMany().getName());
        assertEquals(phone, fromDB.getPhone());
    }

    @Test
    @Transactional
    public void testOne() {
        String name = "testManC";
        String phone = "01011112222";

        personCreateService.one(name, phone);
        PersonOnly formDB = personOnlyRepository.findByPhone(phone).get();

        assertEquals(name, formDB.getName());
        assertEquals(phone, formDB.getPhone());
    }
}
