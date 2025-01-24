package com.turi.premium.domain.exception;

import java.io.Serial;

public final class PremiumActivatedException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -5601586700980561438L;

    public PremiumActivatedException(final Long premiumId)
    {
        super(String.format("Premium with Id '%s' is already paid and activated.", premiumId));
    }
}