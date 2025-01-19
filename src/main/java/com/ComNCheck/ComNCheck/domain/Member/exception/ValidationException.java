package com.ComNCheck.ComNCheck.domain.Member.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
