package com.turi.payment.domain.exception;

import java.io.Serial;

public final class PaymentStripeException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -3883776976148444223L;

    public PaymentStripeException(final String message)
    {
        super(String.format("Payment by stripe failed with the message: '%s'", message));
    }
}
