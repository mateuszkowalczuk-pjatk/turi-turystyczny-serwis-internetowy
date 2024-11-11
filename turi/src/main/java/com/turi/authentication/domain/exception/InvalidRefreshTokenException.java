package com.turi.authentication.domain.exception;

import java.io.Serial;

public final class InvalidRefreshTokenException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 2125376449269206985L;

    public InvalidRefreshTokenException()
    {
        super("The specified refresh token is invalid. Parameters userId, token and expiresAt are required!");
    }
}
