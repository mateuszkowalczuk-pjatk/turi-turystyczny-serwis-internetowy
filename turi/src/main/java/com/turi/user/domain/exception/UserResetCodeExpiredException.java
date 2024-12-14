package com.turi.user.domain.exception;

import java.io.Serial;

public final class UserResetCodeExpiredException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 8762901852348788710L;

    public UserResetCodeExpiredException()
    {
        super("User reset code has expired!");
    }
}