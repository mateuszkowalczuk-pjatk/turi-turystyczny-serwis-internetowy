package com.turi.payment.domain.port;

import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentStripeResponse;

public interface PaymentService
{
    Boolean isPaymentForPremiumSucceeded(final Long premiumId);

    PaymentStripeResponse payForPremium(final Payment payment);

    void handleStripeWebhook(final String payload, final String sigHeader);
}
