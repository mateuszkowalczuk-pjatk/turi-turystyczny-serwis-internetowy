package com.turi.account.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class AccountNotFoundException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = 5669617529909012495L;

    public AccountNotFoundException(final Long accountId)
    {
        super(HttpStatus.NOT_FOUND, String.format("Account with Id '%s' not found.", accountId));
    }
}
