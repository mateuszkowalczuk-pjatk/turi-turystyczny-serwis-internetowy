package com.turi.premium.domain.exception;

import java.io.Serial;

public final class PremiumLoginCodeExpiredException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -8848634694620093933L;

    public PremiumLoginCodeExpiredException()
    {
        super("Premium login code has expired!");
    }
}
