package com.turi.account.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class InvalidAccountException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = 1497362616786599089L;

    public InvalidAccountException()
    {
        super(HttpStatus.BAD_REQUEST, "The specified account is invalid. Parameters account type, login, email and password are required!");
    }
}
