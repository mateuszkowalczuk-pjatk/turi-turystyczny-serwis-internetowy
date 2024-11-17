package com.turi.premium.domain.exception;

import java.io.Serial;

public final class PremiumNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 6957405604627183586L;

    public PremiumNotFoundException(final Long premiumId)
    {
        super(String.format("Premium with Id '%s' not found.", premiumId));
    }
}
