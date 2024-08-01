package com.turi.address.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class InvalidAddressException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = -219099189628208745L;

    public InvalidAddressException()
    {
        super(HttpStatus.BAD_REQUEST, "The specified address is invalid. Parameters country, city, zipCode, street and buildingNumber are required!");
    }
}
