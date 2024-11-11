package com.turi.user.domain.exception;

import java.io.Serial;

public final class InvalidUserPasswordResetCode extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -4016599296208075139L;

    public InvalidUserPasswordResetCode()
    {
        super("Invalid user password reset code.");
    }
}
