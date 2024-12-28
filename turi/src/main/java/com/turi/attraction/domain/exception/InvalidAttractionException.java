package com.turi.attraction.domain.exception;

import java.io.Serial;

public final class InvalidAttractionException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 6795342305613553232L;

    public InvalidAttractionException()
    {
        super("The specified attraction is invalid. Parameters touristicPlaceId, attractionType, name, description, price, priceType, prepayment, dataFrom, dateTo, hourFrom, hourTo and daysReservationBefore are required!");
    }
}
