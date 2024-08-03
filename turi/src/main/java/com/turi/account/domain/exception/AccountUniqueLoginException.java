package com.turi.account.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class AccountUniqueLoginException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = 311274559817545836L;

    public AccountUniqueLoginException(final String login)
    {
        super(HttpStatus.BAD_REQUEST, String.format("Account with login '%s' already exists. Login for account must be unique.", login));
    }
}
