package com.turi.payment.domain.port;

import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentStripeResponse;

public interface StripeService
{
    PaymentStripeResponse checkout(final Payment payment);

    PaymentStripeResponse webhook(final String payload, final String sigHeader);
}
