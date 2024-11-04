package com.turi.authentication.domain.exception;

import java.io.Serial;

public final class RefreshTokenNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -5714857388811767971L;

    public RefreshTokenNotFoundException(final Long refreshTokenId)
    {
        super(String.format("Refresh token with Id '%s' not found.", refreshTokenId));
    }
}
