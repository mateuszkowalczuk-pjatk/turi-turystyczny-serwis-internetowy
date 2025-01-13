package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class ReservationCompletedException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -2184805112614125567L;

    public ReservationCompletedException(final Long reservationId)
    {
        super(String.format("The specified reservation with Id '%s' is already realized or canceled.", reservationId));
    }
}
