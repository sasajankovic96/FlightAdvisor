package com.sasajankovic.domain.exceptions;

/** Represents status code 409 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
