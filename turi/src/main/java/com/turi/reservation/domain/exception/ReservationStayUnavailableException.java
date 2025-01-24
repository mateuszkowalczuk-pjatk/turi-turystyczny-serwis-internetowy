package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class ReservationStayUnavailableException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 7159999711640915650L;

    public ReservationStayUnavailableException(final Long stayId)
    {
        super(String.format("Reservation stay with Id '%s' is unavailable in provided date.", stayId));
    }
}
