package com.turi.authentication.domain.exception;

import java.io.Serial;

public class RefreshTokenNotFoundByTokenException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -4860279726514607936L;

    public RefreshTokenNotFoundByTokenException(final String refreshToken)
    {
        super(String.format("Refresh token with token '%s' not found.", refreshToken));
    }
}
