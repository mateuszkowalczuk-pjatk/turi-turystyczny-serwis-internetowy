package com.turi.payment.domain.exception;

import java.io.Serial;

public final class InvalidStripePaymentException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = 980960273902640757L;

    public InvalidStripePaymentException()
    {
        super("The specified stripe payment is invalid. Parameters intent, status and paymentDate are required!");
    }
}