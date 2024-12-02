package com.turi.payment.domain.exception;

import java.io.Serial;

public final class PaymentForPremiumFailedException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -5026654498399338516L;

    public PaymentForPremiumFailedException()
    {
        super("");
    }
}