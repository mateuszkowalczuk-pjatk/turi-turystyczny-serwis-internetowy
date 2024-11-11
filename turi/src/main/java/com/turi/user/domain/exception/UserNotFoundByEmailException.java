package com.turi.user.domain.exception;

import java.io.Serial;

public final class UserNotFoundByEmailException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 7313994773331183280L;

    public UserNotFoundByEmailException(final String email)
    {
        super(String.format("User with email '%s' not found.", email));
    }
}