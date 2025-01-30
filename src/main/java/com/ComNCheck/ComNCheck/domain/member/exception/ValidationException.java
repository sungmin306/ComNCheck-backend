package com.ComNCheck.ComNCheck.domain.member.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
