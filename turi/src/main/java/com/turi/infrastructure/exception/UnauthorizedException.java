package com.turi.infrastructure.exception;

import java.io.Serial;

public final class UnauthorizedException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -2872662183889194668L;

    public UnauthorizedException(final String message)
    {
        super(message);
    }
}