package com.turi.address.domain.exception;

import java.io.Serial;

public final class AddressNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 8484658392415406455L;

    public AddressNotFoundException(final Long addressId)
    {
        super(String.format("Address with Id '%s' not found.", addressId));
    }
}