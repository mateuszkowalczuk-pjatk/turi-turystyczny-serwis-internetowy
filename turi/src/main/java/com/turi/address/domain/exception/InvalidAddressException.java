package com.turi.address.domain.exception;

import java.io.Serial;

public final class InvalidAddressException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -219099189628208745L;

    public InvalidAddressException()
    {
        super("The specified address is invalid. Parameters country, city, zipCode, street and buildingNumber are required!");
    }
}
