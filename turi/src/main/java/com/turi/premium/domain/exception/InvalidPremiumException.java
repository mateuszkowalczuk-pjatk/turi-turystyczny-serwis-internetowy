package com.turi.premium.domain.exception;

import java.io.Serial;

public final class InvalidPremiumException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -4578678956945398068L;

    public InvalidPremiumException()
    {
        super("The specified premium is invalid. Parameters x, x and x are required!");
    }
}
