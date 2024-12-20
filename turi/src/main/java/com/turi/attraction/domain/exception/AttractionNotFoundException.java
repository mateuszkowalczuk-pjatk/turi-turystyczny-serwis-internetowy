package com.turi.attraction.domain.exception;

import java.io.Serial;

public final class AttractionNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 4581005155968995842L;

    public AttractionNotFoundException(final Long attractionId)
    {
        super(String.format("Attraction with Id '%s' not found.", attractionId));
    }
}
