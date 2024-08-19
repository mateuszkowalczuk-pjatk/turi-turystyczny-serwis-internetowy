package com.turi.user.domain.exception;

import java.io.Serial;

public final class UserNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 5669617529909012495L;

    public UserNotFoundException(final Long userId)
    {
        super(String.format("User with Id '%s' not found.", userId));
    }
}
