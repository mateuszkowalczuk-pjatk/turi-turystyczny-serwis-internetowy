package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class ReservationAttractionException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 3349557614285092703L;

    public ReservationAttractionException(final Long attractionId)
    {
        super(String.format("Reservation attraction with Id '%s' does not belong to provided touristic place.", attractionId));
    }
}
