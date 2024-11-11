package com.turi.authentication.domain.exception;

import java.io.Serial;

public final class UnauthorizedException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 7362289919885229537L;

    public UnauthorizedException()
    {
        super("Account not authenticated by access token.");
    }
}
