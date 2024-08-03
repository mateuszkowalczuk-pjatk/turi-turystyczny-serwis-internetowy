package com.turi.account.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class AccountUniqueEmailException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = -2590837915229369481L;

    public AccountUniqueEmailException(final String email)
    {
        super(HttpStatus.BAD_REQUEST, String.format("Account with email '%s' already exists. Email for account must be unique.", email));
    }
}
