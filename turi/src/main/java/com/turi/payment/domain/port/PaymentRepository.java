package com.turi.payment.domain.port;

import com.turi.payment.domain.model.Payment;

import java.util.List;

public interface PaymentRepository
{
    List<Payment> findAllByPremiumId(final Long accountId);

    Payment findById(final Long id);

    Payment findByStripeId(final Long stripeId);

    Long insert(final Payment payment);

    void update(final Long id, final Payment payment);
}
