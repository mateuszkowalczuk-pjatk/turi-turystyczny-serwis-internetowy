package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.exception.PaymentForPremiumFailedException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentName;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.payment.domain.model.PaymentStripeResponse;
import com.turi.payment.domain.port.PaymentRepository;
import com.turi.payment.domain.port.PaymentService;
import com.turi.payment.domain.port.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService
{
    private final StripeService stripeService;
    private final PaymentRepository repository;

    @Override
    public Boolean isPaymentForPremiumSucceeded(final Long premiumId)
    {
        final var payment = repository.findAllByPremiumId(premiumId).stream()
                .max(Comparator.comparingLong(Payment::getPaymentId))
                .orElse(null);

        if (payment == null)
        {
            return false;
        }

        final var stripePayment = stripeService.getByIntent(payment.getStripePaymentIntent());

        if (stripePayment == null)
        {
            return false;
        }

        payment.setPaymentDate(stripePayment.getPaymentDate());
        payment.setStatus(stripePayment.getStatus());

        repository.update(payment.getPaymentId(), payment);

        if (payment.getStatus() == PaymentStatus.FAILED)
        {
            throw new PaymentForPremiumFailedException(premiumId);
        }

        return payment.getStatus() == PaymentStatus.SUCCEEDED;
    }

    @Override
    public PaymentStripeResponse payForPremium(final Payment payment)
    {
        final var response = stripeService.checkout(payment, PaymentName.PREMIUM);

        payment.setStripeId(response.getStripeId());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(response.getStatus());

        repository.insert(payment);

        return response;
    }

    @Override
    public void handleStripeWebhook(final String payload, final String sigHeader)
    {
        final var response = stripeService.webhook(payload, sigHeader);

        if (response != null)
        {
            final var payment = repository.findByStripeId(response.getStripeId());
            payment.setStripePaymentIntent(response.getStripePaymentIntent());
            payment.setPaymentDate(LocalDateTime.now());

            repository.update(payment.getPaymentId(), payment);
        }
    }
}
