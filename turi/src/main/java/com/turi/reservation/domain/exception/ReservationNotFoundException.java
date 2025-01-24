package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class ReservationNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -5398927175458402358L;

    public ReservationNotFoundException(final Long reservationId)
    {
        super(String.format("Reservation with Id '%s' not found.", reservationId));
    }
}
