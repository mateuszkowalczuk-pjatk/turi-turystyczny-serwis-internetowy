package com.turi.offer.domain.exception;

import java.io.Serial;

public final class UniqueFavouriteException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -6736832552664514380L;

    public UniqueFavouriteException()
    {
        super("The specified favourite offer already exists. Parameters accountId and touristicPlaceId must not be repeated!");
    }
}
