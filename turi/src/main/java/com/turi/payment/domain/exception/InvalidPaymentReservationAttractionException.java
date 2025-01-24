package com.turi.payment.domain.exception;

import java.io.Serial;

public final class InvalidPaymentReservationAttractionException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -3485509110992106244L;

    public InvalidPaymentReservationAttractionException()
    {
        super("The specified payment reservation attraction is invalid. Parameters paymentId and reservationAttractionId are required!");
    }
}
