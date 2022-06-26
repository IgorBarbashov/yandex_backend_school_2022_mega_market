package ru.yandex.backend.school2.megamarket.exception;

public class RestApiErrorResponse {

    private int code;

    private String message;

    public RestApiErrorResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
