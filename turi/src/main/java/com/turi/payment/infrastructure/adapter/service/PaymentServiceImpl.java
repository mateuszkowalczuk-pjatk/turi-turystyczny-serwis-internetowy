package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.exception.PaymentForPremiumFailedException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.payment.domain.model.PaymentStripeResponse;
import com.turi.payment.domain.port.PaymentRepository;
import com.turi.payment.domain.port.PaymentService;
import com.turi.payment.domain.port.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService
{
    private final StripeService stripeService;
    private final PaymentRepository repository;

    @Override
    public Boolean isPaymentForPremiumSucceeded(final Long accountId)
    {
        final var payment = Objects.requireNonNull(repository.findAllByPremiumId(accountId).stream()
                .max(Comparator.comparingLong(Payment::getPaymentId))
                .orElse(null));

        if (payment.getStatus() == PaymentStatus.FAILED)
        {
            throw new PaymentForPremiumFailedException();
        }

        return payment.getStatus() == PaymentStatus.SUCCEEDED;
    }

    @Override
    public PaymentStripeResponse payForPremium(final Payment payment)
    {
        final var response = stripeService.checkout(payment);

        payment.setStripeId(response.getStripeId());
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus(response.getStatus());

        repository.insert(payment);

        return response;
    }

    @Override
    public void handleStripeWebhook(final String payload, final String sigHeader)
    {
        final var response = stripeService.webhook(payload, sigHeader);

        final var payment = repository.findByStripeId(response.getStripeId());
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus(response.getStatus());

        repository.update(payment.getPaymentId(), payment);
    }
}
