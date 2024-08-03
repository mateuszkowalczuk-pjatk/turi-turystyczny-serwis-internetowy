package com.turi.account.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class AccountUniqueAddressException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = 4807422830784154612L;

    public AccountUniqueAddressException(final Long addressId)
    {
        super(HttpStatus.BAD_REQUEST, String.format("Account with address Id '%s' already exists. Address for account must be unique.", addressId));
    }
}
