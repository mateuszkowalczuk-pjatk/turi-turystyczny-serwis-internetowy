package com.turi.touristicplace.domain.exception;

import java.io.Serial;

public final class TouristicPlaceUniqueAddressException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 189608245519884241L;

    public TouristicPlaceUniqueAddressException(final Long addressId)
    {
        super(String.format("Touristic place with address Id '%s' already exists. Address for touristic place must be unique.", addressId));
    }
}
