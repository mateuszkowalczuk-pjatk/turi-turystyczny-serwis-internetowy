package com.turi.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public class InvalidUserException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = -6231559988172838728L;

    public InvalidUserException()
    {
        super(HttpStatus.BAD_REQUEST, "The specified user is invalid. Parameters username, email and password are required!");
    }
}