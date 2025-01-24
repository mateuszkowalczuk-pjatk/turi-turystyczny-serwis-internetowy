package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class ReservationUnpaidException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 7159999711640915650L;

    public ReservationUnpaidException(final Long reservationId)
    {
        super(String.format("Reservation with Id '%s' is unpaid.", reservationId));
    }
}