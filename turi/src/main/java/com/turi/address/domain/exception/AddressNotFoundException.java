package com.turi.address.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.Serial;

public final class AddressNotFoundException extends ResponseStatusException
{
    @Serial
    private static final long serialVersionUID = 8484658392415406455L;

    public AddressNotFoundException(final Long addressId)
    {
        super(HttpStatus.NOT_FOUND, String.format("Address with Id '%s' not found.", addressId));
    }
}