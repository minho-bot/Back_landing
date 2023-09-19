package osteam.backland.global.exception.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CommonException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public CommonException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}