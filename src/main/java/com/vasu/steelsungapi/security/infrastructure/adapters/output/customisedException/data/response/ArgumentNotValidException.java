package com.vasu.steelsungapi.security.infrastructure.adapters.output.customisedException.data.response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ArgumentNotValidException extends RuntimeException {
    private String fieldValue;

    public ArgumentNotValidException(String fieldValue) {
        super(String.format("Parameter %s is not valid ",fieldValue));
        this.fieldValue = fieldValue;
    }

    public String getFieldValue() {
        return fieldValue;
    }
}
