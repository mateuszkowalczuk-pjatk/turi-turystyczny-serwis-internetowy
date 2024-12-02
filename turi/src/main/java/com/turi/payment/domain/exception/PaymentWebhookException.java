package com.turi.payment.domain.exception;

import java.io.Serial;

public final class PaymentWebhookException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -5974739589585088953L;

    public PaymentWebhookException()
    {
        super("");
    }
}
