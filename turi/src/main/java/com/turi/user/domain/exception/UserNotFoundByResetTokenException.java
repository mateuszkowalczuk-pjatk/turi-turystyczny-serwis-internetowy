package com.turi.user.domain.exception;

import java.io.Serial;

public final class UserNotFoundByResetTokenException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 795114509805350448L;

    public UserNotFoundByResetTokenException(final String resetToken)
    {
        super(String.format("User not found by resetToken '%s'.", resetToken));
    }
}