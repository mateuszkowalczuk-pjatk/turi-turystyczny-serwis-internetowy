package com.turi.payment.domain.port;

public interface PaymentReservationAttractionRepository
{
    Boolean isPaymentForReservationAttraction(final Long paymentId, final Long reservationAttractionId);

    void insert(final Long paymentId, final Long reservationAttractionId);
}
