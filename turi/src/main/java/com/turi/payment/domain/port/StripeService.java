package com.turi.payment.domain.port;

import com.turi.payment.domain.model.Payment;

public interface StripeService
{
    String createCheckoutSession(final Payment payment);

    void handleWebhook(final String payload, final String sigHeader);
}
