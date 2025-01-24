package com.turi.payment.domain.port;

import com.turi.payment.domain.model.Payment;
import com.turi.reservation.domain.model.ReservationAttraction;

import java.util.List;

public interface PaymentService
{
    Boolean isPaymentForPremiumSucceeded(final Long premiumId);

    Boolean isPaymentForReservationSucceeded(final Long reservationId);

    String payForPremium(final Payment payment);

    String payForReservation(final Payment payment, final List<ReservationAttraction> reservationAttractions);

    void handleStripeWebhook(final String payload, final String sigHeader);
}
