package com.turi.touristicplace.domain.exception;

import java.io.Serial;

public final class TouristicPlaceNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 5669617529909012495L;

    public TouristicPlaceNotFoundException(final Long touristicPlaceId)
    {
        super(String.format("Touristic place with Id '%s' not found.", touristicPlaceId));
    }
}
