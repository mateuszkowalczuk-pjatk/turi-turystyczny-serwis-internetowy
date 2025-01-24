package com.turi.payment.domain.exception;

import java.io.Serial;

public final class PaymentForReservationFailedException extends RuntimeException
{
    @Serial
    private static final long serialVersionUID = -464323743428787662L;

    public PaymentForReservationFailedException(final Long reservationId)
    {
        super(String.format("Payment for reservation with Id '%s' failed!", reservationId));
    }
}