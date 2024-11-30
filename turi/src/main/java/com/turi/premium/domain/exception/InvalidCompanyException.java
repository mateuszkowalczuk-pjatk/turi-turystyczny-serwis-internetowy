package com.turi.premium.domain.exception;

import java.io.Serial;

public final class InvalidCompanyException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 1669017116527113015L;

    public InvalidCompanyException()
    {
        super("The specified user company is invalid. Enter your proper company name, address and NIP!");
    }
}
