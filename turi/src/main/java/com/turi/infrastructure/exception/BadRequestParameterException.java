package com.turi.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class BadRequestParameterException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = -6918162623647065886L;

    public BadRequestParameterException(final String message)
    {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
