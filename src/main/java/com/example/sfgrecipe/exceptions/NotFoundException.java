package com.example.sfgrecipe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private final Long id;

    public NotFoundException() {
        super();
        id = null;
    }

    public NotFoundException(String message) {
        super(message);
        id = null;
    }

    public NotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
        id = null;
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        id = null;
    }

    public Long getId() {
        return id;
    }
}
