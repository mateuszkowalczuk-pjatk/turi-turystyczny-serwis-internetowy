package com.turi.touristicplace.domain.exception;

import java.io.Serial;

public final class GuaranteedServiceNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 421063380653583548L;

    public GuaranteedServiceNotFoundException(final Long guaranteedServiceId)
    {
        super(String.format("Guaranteed service with Id '%s' not found.", guaranteedServiceId));
    }
}
