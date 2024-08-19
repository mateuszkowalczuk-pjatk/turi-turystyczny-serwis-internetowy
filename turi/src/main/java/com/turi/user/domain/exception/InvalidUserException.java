package com.turi.user.domain.exception;

import java.io.Serial;

public final class InvalidUserException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -6231559988172838728L;

    public InvalidUserException()
    {
        super("The specified user is invalid. Parameters username, email and password are required!");
    }
}