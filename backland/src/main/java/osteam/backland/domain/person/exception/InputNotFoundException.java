package osteam.backland.domain.person.exception;

import org.springframework.http.HttpStatus;
import osteam.backland.global.exception.exception.CommonException;

public class InputNotFoundException extends CommonException {
    public InputNotFoundException(String field) {
        super("INPUT_NOT_FOUND_EXCEPTION", field + "가 비어있습니다.", HttpStatus.NOT_FOUND);
    }
}
