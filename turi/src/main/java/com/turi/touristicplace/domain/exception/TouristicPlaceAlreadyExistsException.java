package com.turi.touristicplace.domain.exception;

import java.io.Serial;

public final class TouristicPlaceAlreadyExistsException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -9109138689030096307L;

    public TouristicPlaceAlreadyExistsException()
    {
        super("Touristic place for specified premium already exists.");
    }
}