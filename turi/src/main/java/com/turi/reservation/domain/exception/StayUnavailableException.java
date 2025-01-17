package com.turi.reservation.domain.exception;

import java.io.Serial;

public final class StayUnavailableException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 7159999711640915650L;

    public StayUnavailableException(final Long stayId)
    {
        super(String.format("Stay with Id '%s' is unavailable in provided date.", stayId));
    }
}
