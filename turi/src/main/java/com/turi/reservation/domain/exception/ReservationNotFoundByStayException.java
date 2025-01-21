package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class ReservationNotFoundByStayException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -4491750836099647984L;

    public ReservationNotFoundByStayException(final Long stayId)
    {
        super(String.format("Reservation with stay Id '%s' not found.", stayId));
    }
}
