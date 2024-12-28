package com.turi.touristicplace.domain.exception;

import java.io.Serial;

public final class InvalidTouristicPlaceException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -7833182704794488562L;

    public InvalidTouristicPlaceException()
    {
        super("The specified touristic place is invalid. Parameter premiumId is required!");
    }
}
