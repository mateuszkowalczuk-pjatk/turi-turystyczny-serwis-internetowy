package com.turi.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class UserUniqueEmailException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = -2590837915229369481L;

    public UserUniqueEmailException(final String email)
    {
        super(HttpStatus.BAD_REQUEST, String.format("User with email '%s' already exists. Email for user must be unique.", email));
    }
}
