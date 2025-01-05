package com.turi.offer.domain.exception;

import java.io.Serial;

public final class InvalidFavouriteException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 2993643899730719950L;

    public InvalidFavouriteException()
    {
        super("The specified favourite offer is invalid. Parameters accountId and touristicPlaceId are required!");
    }
}
