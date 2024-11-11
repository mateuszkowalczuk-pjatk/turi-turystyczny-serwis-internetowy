package com.turi.authentication.domain.exception;

import java.io.Serial;

public final class RefreshTokenExpiredException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -5328189999572025395L;

    public RefreshTokenExpiredException()
    {
        super("The specified refresh token expired!");
    }
}
