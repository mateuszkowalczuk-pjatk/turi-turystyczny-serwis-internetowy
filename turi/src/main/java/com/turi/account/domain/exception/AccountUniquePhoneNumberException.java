package com.turi.account.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class AccountUniquePhoneNumberException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = 4807422830784154612L;

    public AccountUniquePhoneNumberException(final String phoneNumber)
    {
        super(HttpStatus.BAD_REQUEST, String.format("Account with phone number '%s' already exists. Phone number for account must be unique.", phoneNumber));
    }
}
