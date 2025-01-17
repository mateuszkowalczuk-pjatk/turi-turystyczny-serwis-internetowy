package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class AttractionUnavailableException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -493239217608385634L;

    public AttractionUnavailableException(final Long attractionId)
    {
        super(String.format("Attraction with Id '%s' is unavailable in provided date.", attractionId));
    }
}
