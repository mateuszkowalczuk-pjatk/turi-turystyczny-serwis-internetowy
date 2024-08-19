package com.turi.account.domain.exception;

import java.io.Serial;

public final class AccountUniqueAddressException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 189608245519884241L;

    public AccountUniqueAddressException(final Long addressId)
    {
        super(String.format("Account with address Id '%s' already exists. Address for account must be unique.", addressId));
    }
}
