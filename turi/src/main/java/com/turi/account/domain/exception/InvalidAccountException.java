package com.turi.account.domain.exception;

import java.io.Serial;

public final class InvalidAccountException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 1497362616786599089L;

    public InvalidAccountException()
    {
        super("The specified account is invalid. Parameters userId and accountType are required!");
    }
}
