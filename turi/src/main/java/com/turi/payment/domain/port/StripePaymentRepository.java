package com.turi.payment.domain.port;

import com.turi.payment.domain.model.StripePayment;

public interface StripePaymentRepository
{
    StripePayment findByIntent(final String intent);

    void insert(final StripePayment stripePayment);
}
