package com.turi.account.domain.exception;

import java.io.Serial;

public final class AccountActivationCodeExpiredException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 7855381730845512880L;

    public AccountActivationCodeExpiredException()
    {
        super("Account activation code has expired. A new activation code has been sent.");
    }
}