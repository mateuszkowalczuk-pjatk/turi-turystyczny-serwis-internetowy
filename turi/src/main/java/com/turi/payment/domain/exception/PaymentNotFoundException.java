package com.turi.payment.domain.exception;

import java.io.Serial;

public final class PaymentNotFoundException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 5962362840732411567L;

    public PaymentNotFoundException(final Long paymentId)
    {
        super(String.format("Payment with Id '%s' not found.", paymentId));
    }
}
