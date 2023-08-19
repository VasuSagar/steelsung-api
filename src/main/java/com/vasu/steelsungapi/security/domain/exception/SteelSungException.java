package com.vasu.steelsungapi.security.domain.exception;

import org.springframework.http.HttpStatus;

public class SteelSungException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public SteelSungException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public SteelSungException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
