package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class InvalidReservationException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -6044820358478269549L;

    public InvalidReservationException()
    {
        super("The specified reservation is invalid. Parameters stayId, accountId, dateFrom, dateTo, checkInTime and status are required!");
    }
}
