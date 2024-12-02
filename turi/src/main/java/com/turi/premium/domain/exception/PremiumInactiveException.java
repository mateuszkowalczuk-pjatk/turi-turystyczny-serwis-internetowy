package com.turi.premium.domain.exception;

import java.io.Serial;

public final class PremiumInactiveException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -4144780256215250638L;

    public PremiumInactiveException(final Long premiumId)
    {
        super(String.format("Premium with Id '%s' is inactivate.", premiumId));
    }
}