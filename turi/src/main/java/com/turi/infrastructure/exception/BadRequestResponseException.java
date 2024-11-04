package com.turi.infrastructure.exception;

import java.io.Serial;

public final class BadRequestResponseException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -9074791398351096978L;

    public BadRequestResponseException(final String message)
    {
        super(message);
    }
}
