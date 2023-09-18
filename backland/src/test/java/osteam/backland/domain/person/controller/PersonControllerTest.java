package osteam.backland.domain.person.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import osteam.backland.domain.person.controller.request.PersonCreateRequest;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.service.PersonCreateService;
import osteam.backland.domain.phone.entity.PhoneOneToOne;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mock;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    void longNamePersonTest() throws Exception {
        String longName = "teamaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String longNamePerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        longName,
                        "01012341234"
                ));
        mock.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(longNamePerson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(longName));
    }

    @Test
    @Transactional
    void longPhonePersonTest() throws Exception {
        String longPhone = "010123412341111111111111111111111";
        String longPhonePerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        "team",
                        longPhone
                ));
        mock.perform(post("/person")
                .content(longPhonePerson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value(longPhone));
    }

    @Test
    @Transactional
    void shortPhonePersonTest() throws Exception {
        String shortPhonePerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        "team",
                        "010"
                ));
        mock.perform(post("/person")
                .content(shortPhonePerson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("team"))
                .andExpect(jsonPath("$.phone").value("010"));
    }

    @Test
    @Transactional
    void nullPersonTest() throws Exception {
        String nullPerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        null,
                        null
                ));
        mock.perform(post("/person")
                .content(nullPerson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void blankPersonTest() throws Exception {
        String blankPerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        "",
                        ""
                ));
        mock.perform(post("/person")
                .content(blankPerson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void nullInputTest() throws Exception {
        String longPhonePerson = objectMapper
                .writeValueAsString(null);
        mock.perform(post("/person")
                .content(longPhonePerson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Transactional
    void changeNamePersonTest() throws Exception {
        String originName = "sos";
        String phone = "01012341234";
        String changeName = "team";

        String successPerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        originName,
                        phone
                ));
        mock.perform(post("/person")
                .content(successPerson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("sos"))
                .andExpect(jsonPath("$.phone").value("01012341234"));

        String changePerson = objectMapper
                .writeValueAsString(new PersonCreateRequest(
                        changeName,
                        phone
                ));

        mock.perform(post("/person")
                .content(changePerson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("team"))
                .andExpect(jsonPath("$.phone").value("01012341234"));

    }
}
