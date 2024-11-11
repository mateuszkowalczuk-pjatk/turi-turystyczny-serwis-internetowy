package com.turi.account.domain.exception;

import java.io.Serial;

public final class AccountUniqueUserIdException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -3579027649650167467L;

    public AccountUniqueUserIdException(final Long userId)
    {
        super(String.format("Account with userId '%s' already exists. UserId for account must be unique.", userId));
    }
}
