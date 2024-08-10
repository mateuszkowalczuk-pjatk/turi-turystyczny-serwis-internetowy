package com.turi.user.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class UserNotFoundException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = 5669617529909012495L;

    public UserNotFoundException(final Long userId)
    {
        super(HttpStatus.NOT_FOUND, String.format("User with Id '%s' not found.", userId));
    }
}
