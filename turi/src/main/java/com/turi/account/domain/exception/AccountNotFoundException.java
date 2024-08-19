package com.turi.account.domain.exception;

import java.io.Serial;

public final class AccountNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 5669617529909012495L;

    public AccountNotFoundException(final Long accountId)
    {
        super(String.format("Account with Id '%s' not found.", accountId));
    }
}
