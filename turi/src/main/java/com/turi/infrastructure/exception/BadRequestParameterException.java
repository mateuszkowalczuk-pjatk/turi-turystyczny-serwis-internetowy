package com.turi.infrastructure.exception;

import java.io.Serial;

public final class BadRequestParameterException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -6918162623647065886L;

    public BadRequestParameterException(final String message)
    {
        super(message);
    }
}
