package com.turi.payment.domain.port;

import com.turi.payment.domain.model.Payment;

public interface PaymentService
{
    Boolean isPaymentForPremiumSucceeded(final Long premiumId);

    String payForPremium(final Payment payment);

    void handleStripeWebhook(final String payload, final String sigHeader);
}
