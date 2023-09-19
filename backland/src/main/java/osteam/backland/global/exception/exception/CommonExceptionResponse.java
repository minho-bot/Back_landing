package osteam.backland.global.exception.exception;

import lombok.Data;

@Data
public class CommonExceptionResponse {
    private final String code;
    private final String message;
}
