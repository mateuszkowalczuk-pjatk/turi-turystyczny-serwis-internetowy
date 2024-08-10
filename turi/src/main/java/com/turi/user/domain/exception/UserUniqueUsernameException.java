package com.turi.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class UserUniqueUsernameException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = 311274559817545836L;

    public UserUniqueUsernameException(final String username)
    {
        super(HttpStatus.BAD_REQUEST, String.format("User with username '%s' already exists. Username for user must be unique.", username));
    }
}
