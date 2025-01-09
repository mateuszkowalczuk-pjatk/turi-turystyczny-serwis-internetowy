package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class InvalidReservationAttractionException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -2645175436036266343L;

    public InvalidReservationAttractionException()
    {
        super("The specified reservation attraction is invalid. Parameters reservationId, attractionId, dateFrom, hourFrom, hourTo, price and status are required!");
    }
}
