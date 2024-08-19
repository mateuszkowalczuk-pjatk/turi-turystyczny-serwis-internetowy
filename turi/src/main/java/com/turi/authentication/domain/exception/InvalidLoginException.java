package com.turi.authentication.domain.exception;

import java.io.Serial;

public final class InvalidLoginException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -1345062492084368478L;

    public InvalidLoginException(final String login)
    {
        super(String.format("Invalid login. Account for login '%s' not found.", login));
    }
}