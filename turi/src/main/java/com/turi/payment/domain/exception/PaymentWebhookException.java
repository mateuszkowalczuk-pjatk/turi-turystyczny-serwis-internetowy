package com.turi.payment.domain.exception;

import java.io.Serial;

public final class PaymentWebhookException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -5974739589585088953L;

    public PaymentWebhookException(final String message)
    {
        super(String.format("Payment processing by stripe webhook failed with the message: '%s'", message));
    }
}
