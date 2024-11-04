package com.turi.account.domain.exception;

import java.io.Serial;

public final class AccountActivationCodeRecentlySentException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 6362158193674888393L;

    public AccountActivationCodeRecentlySentException()
    {
        super("The account activation code cannot be sent again because it was sent less than 5 minutes ago.");
    }
}
