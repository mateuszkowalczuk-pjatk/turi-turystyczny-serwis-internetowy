package com.turi.stay.domain.exception;

import java.io.Serial;

public final class StayNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 5669617529909012495L;

    public StayNotFoundException(final Long stayId)
    {
        super(String.format("Stay with Id '%s' not found.", stayId));
    }
}
