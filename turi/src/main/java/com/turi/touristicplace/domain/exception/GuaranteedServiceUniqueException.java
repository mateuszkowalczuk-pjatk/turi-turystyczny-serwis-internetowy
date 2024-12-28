package com.turi.touristicplace.domain.exception;

import java.io.Serial;

public final class GuaranteedServiceUniqueException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 4535058890903102647L;

    public GuaranteedServiceUniqueException(final Long touristicPlaceId, final String service)
    {
        super(String.format("Guaranteed service with touristicPlaceId '%s' and service '%s' already exists.", touristicPlaceId, service));
    }
}
