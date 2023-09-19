package osteam.backland.global.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import osteam.backland.global.exception.exception.CommonException;
import osteam.backland.global.exception.exception.CommonExceptionResponse;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonExceptionResponse> commonExceptionHandler(CommonException e) {
        return new ResponseEntity(
                new CommonExceptionResponse(
                        e.getCode(),
                        e.getMessage()
                ),
                e.getStatus()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CommonExceptionResponse> runtimeExceptionHandler(RuntimeException e) {
        e.printStackTrace();
        return new ResponseEntity<>(
                new CommonExceptionResponse(
                        "INTERNAL_SERVER_ERROR",
                        e.getMessage() != null ? e.getMessage() : "알 수 없는 오류"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonExceptionResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return new CommonExceptionResponse(
                "INVALID_REQUEST",
                e.getBindingResult().getFieldError().getDefaultMessage()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonExceptionResponse httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        return new CommonExceptionResponse(
                "INVALID_JSON",
                "JSON 형식이 잘못되었습니다."
        );
    }
}
