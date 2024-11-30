package com.turi.payment.domain.port;

import com.turi.payment.domain.model.Payment;

public interface PaymentService
{
    Payment getById(final Long id);

    String payForPremium();

    void handleStripeWebhook(final String payload, final String sigHeader);
}
