package com.turi.payment.domain.exception;

import java.io.Serial;

public final class InvalidPaymentException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -1358229742520265795L;

    public InvalidPaymentException()
    {
        super("The specified payment is invalid. Parameters x, x and x are required!");
    }
}
