package ru.yandex.backend.school2.megamarket.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class RestApiGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final String REST_API_NOT_FOUND_MESSAGE = "Item not found";

    private final HttpStatus REST_API_NOT_FOUND_CODE = HttpStatus.NOT_FOUND;

    private final String REST_API_INVALID_DATA_MESSAGE = "Validation Failed";

    private final HttpStatus REST_API_INVALID_DATA_CODE = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(value = {
            ConstraintViolationException.class,
            RestApiInvalidDataException.class
    })
    public ResponseEntity<RestApiErrorResponse> handleException(ValidationException e) {
        RestApiErrorResponse data = new RestApiErrorResponse();
        data.setCode(REST_API_INVALID_DATA_CODE.value());
        data.setMessage(REST_API_INVALID_DATA_MESSAGE);
        return new ResponseEntity<>(data, REST_API_INVALID_DATA_CODE);
    }

    @ExceptionHandler(value = {
            NoSuchElementException.class,
            EmptyResultDataAccessException.class,
            RestApiNotFoundException.class
    })
    public ResponseEntity<RestApiErrorResponse> handleException(RuntimeException e) {
        RestApiErrorResponse data = new RestApiErrorResponse();
        data.setCode(REST_API_NOT_FOUND_CODE.value());
        data.setMessage(REST_API_NOT_FOUND_MESSAGE);
        return new ResponseEntity<>(data, REST_API_NOT_FOUND_CODE);
    }

}
