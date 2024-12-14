package com.turi.premium.domain.exception;

import java.io.Serial;

public final class PremiumNotFoundByLoginTokenException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -4520137320615369455L;

    public PremiumNotFoundByLoginTokenException(final String loginToken)
    {
        super(String.format("Premium not found by loginToken '%s'.", loginToken));
    }
}
