package com.turi.premium.domain.exception;

import java.io.Serial;

public final class PremiumNotFoundByAccountException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -5876968564514577253L;

    public PremiumNotFoundByAccountException(final Long accountId)
    {
        super(String.format("Premium not found by account with Id '%s'.", accountId));
    }
}
