package ru.yandex.backend.school2.megamarket.exception;

import javax.validation.ValidationException;

public class RestApiInvalidDataException extends ValidationException {
    public RestApiInvalidDataException() { super(); }
}
