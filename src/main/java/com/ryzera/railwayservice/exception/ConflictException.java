package com.ryzera.railwayservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // This will result in a 409 Conflict status code
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}