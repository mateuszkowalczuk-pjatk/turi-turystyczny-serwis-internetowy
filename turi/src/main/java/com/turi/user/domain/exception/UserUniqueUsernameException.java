package com.turi.user.domain.exception;

import java.io.Serial;

public final class UserUniqueUsernameException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 311274559817545836L;

    public UserUniqueUsernameException(final String username)
    {
        super(String.format("User with username '%s' already exists. Username for user must be unique.", username));
    }
}
