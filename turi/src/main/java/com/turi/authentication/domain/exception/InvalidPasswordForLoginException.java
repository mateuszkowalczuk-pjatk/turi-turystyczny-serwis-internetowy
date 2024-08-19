package com.turi.authentication.domain.exception;

import java.io.Serial;

public final class InvalidPasswordForLoginException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 5356868556223390508L;

    public InvalidPasswordForLoginException(final String login)
    {
        super(String.format("Invalid password for login '%s'.", login));
    }
}