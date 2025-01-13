package com.turi.payment.domain.port;

public interface PaymentReservationAttractionService
{
    void create(final Long paymentId, final Long reservationAttractionId);
}
