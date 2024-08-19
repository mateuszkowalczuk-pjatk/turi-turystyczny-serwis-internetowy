package com.turi.account.domain.exception;

import java.io.Serial;

public final class AccountUniquePhoneNumberException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 6255402779565794939L;

    public AccountUniquePhoneNumberException(final String phoneNumber)
    {
        super(String.format("Account with phone number '%s' already exists. Phone number for account must be unique.", phoneNumber));
    }
}
