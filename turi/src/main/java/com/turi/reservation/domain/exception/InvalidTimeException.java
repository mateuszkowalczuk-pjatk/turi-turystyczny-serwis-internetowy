package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class InvalidTimeException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -3658705447220079051L;

    public InvalidTimeException()
    {
        super("The specified time is invalid. Parameter hourFrom must be ahead from current time and hourTo must be also ahead from hourTo.");
    }
}
