package com.turi.account.domain.exception;

import java.io.Serial;

public final class InvalidAccountActivationCode extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 1105299766984980008L;

    public InvalidAccountActivationCode()
    {
        super("Invalid account activation code.");
    }
}
