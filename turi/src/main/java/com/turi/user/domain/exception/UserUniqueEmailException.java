package com.turi.user.domain.exception;

import java.io.Serial;

public final class UserUniqueEmailException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -2590837915229369481L;

    public UserUniqueEmailException(final String email)
    {
        super(String.format("User with email '%s' already exists. Email for user must be unique.", email));
    }
}
