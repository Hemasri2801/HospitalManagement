package com.example.hospitalmanagement.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom runtime exception used to handle application-specific errors with HTTP status codes.
 * Allows centralized exception handling with meaningful error responses.
 */

public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
