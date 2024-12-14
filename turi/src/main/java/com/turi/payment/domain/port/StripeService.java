package com.turi.payment.domain.port;

import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentName;
import com.turi.payment.domain.model.StripePaymentResponse;
import com.turi.payment.domain.model.StripePayment;

public interface StripeService
{
    StripePayment getByIntent(final String intent);

    StripePaymentResponse checkout(final Payment payment, final PaymentName paymentName);

    StripePaymentResponse webhook(final String payload, final String sigHeader);
}
