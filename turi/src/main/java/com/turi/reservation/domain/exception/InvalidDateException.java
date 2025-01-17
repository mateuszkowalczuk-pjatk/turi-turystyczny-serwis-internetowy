package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class InvalidDateException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 4666735642696018677L;

    public InvalidDateException()
    {
        super("The specified date is invalid. Parameter dateFrom must be at least a day ahead from today and dateTo must be also at least a day ahead from dateFrom.");
    }
}
