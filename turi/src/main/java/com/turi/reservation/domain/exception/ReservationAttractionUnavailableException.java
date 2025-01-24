package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class ReservationAttractionUnavailableException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -493239217608385634L;

    public ReservationAttractionUnavailableException(final Long attractionId)
    {
        super(String.format("Reservation attraction with Id '%s' is unavailable in provided date.", attractionId));
    }
}
