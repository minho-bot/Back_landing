package osteam.backland.domain.person.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.transaction.annotation.Transactional;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

@SpringBootTest
@AutoConfigureGraphQlTester
public class GraphqlPersonControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;
    @Autowired
    private PersonOnlyRepository personOnlyRepository;

    @Test
    @Transactional
    public void getPeopleTest(){
        personOnlyRepository.deleteAll();
        PersonOnly person1 = new PersonOnly();
        person1.setName("testManA");
        person1.setPhone("01012341234");

        PersonOnly person2 = new PersonOnly();
        person2.setName("testManB");
        person2.setPhone("01043214321");

        personOnlyRepository.save(person1);
        personOnlyRepository.save(person2);

        graphQlTester.documentName("getPeople")
                .execute()
                .path("getPeople[*].name")
                .entityList(String.class)
                .containsExactly("testManA", "testManB");
    }
}
