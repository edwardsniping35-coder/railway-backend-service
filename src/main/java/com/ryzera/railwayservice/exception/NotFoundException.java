package com.ryzera.railwayservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A custom exception thrown when a requested resource is not found in the system.
 * This will be handled by the GlobalExceptionHandler to return a 404 NOT FOUND status.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // This annotation helps Spring identify the status code
public class NotFoundException extends RuntimeException {

    /**
     * Constructor that accepts a custom error message.
     * @param message The detailed error message.
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor that accepts a message and the original cause of the exception.
     * @param message The detailed error message.
     * @param cause The original throwable that caused this exception.
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}