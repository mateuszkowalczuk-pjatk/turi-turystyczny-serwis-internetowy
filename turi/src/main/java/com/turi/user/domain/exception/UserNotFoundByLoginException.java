package com.turi.user.domain.exception;

import java.io.Serial;

public final class UserNotFoundByLoginException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 515357519168964893L;

    public UserNotFoundByLoginException(final String login)
    {
        super(String.format("User with login '%s' not found.", login));
    }
}