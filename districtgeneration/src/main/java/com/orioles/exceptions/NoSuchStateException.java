package com.orioles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchStateException extends RuntimeException {
    public NoSuchStateException() {
        super();
    }

    public NoSuchStateException(String message) {
        super(message);
    }
}
