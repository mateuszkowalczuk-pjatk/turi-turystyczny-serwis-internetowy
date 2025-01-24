package com.turi.payment.domain.exception;

import java.io.Serial;

public final class UniquePaymentReservationAttractionException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -3710083875309609116L;

    public UniquePaymentReservationAttractionException()
    {
        super("The specified payment reservation attraction already exists. Parameters paymentId and reservationAttractionId must not be repeated!");
    }
}
