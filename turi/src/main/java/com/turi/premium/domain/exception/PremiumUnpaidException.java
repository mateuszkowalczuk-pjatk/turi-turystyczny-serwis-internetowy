package com.turi.premium.domain.exception;

import java.io.Serial;

public final class PremiumUnpaidException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 2725145360276370744L;

    public PremiumUnpaidException(final Long premiumId)
    {
        super(String.format("Premium for account with Id '%s' is unpaid.", premiumId));
    }
}