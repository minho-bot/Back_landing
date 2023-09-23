package osteam.backland.domain.person.exception;

import org.springframework.http.HttpStatus;
import osteam.backland.global.exception.exception.CommonException;

public class PersonNotFoundException extends CommonException {
    public PersonNotFoundException(String field) {
        super("PERSON_NOT_FOUND_EXCEPTION", field + " : not found", HttpStatus.NOT_FOUND);
    }
}