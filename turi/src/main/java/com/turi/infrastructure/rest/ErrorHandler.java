package com.turi.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorHandler
{
    protected ResponseEntity<ErrorCode> createResponse(final HttpStatus status, final String message)
    {
        final var errorCode = new ErrorCode(status, message);

        return ResponseEntity.status(status).body(errorCode);
    }
}
