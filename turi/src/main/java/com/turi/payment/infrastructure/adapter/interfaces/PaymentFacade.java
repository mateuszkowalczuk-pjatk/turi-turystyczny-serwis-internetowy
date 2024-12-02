package com.turi.payment.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStripeResponse;
import com.turi.payment.domain.port.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class PaymentFacade
{
    private final PaymentService service;

    public ResponseEntity<?> handleStripeSuccess(final String stripeId)
    {
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> handleStripeCancel(final String stripeId)
    {
        return ResponseEntity.ok().build();
    }

    public Boolean isPaymentForPremiumSucceeded(final Long premiumId)
    {
        return service.isPaymentForPremiumSucceeded(premiumId);
    }

    public PaymentStripeResponse payForPremium(final Long premiumId, final Double price, final PaymentMethod method)
    {
        if (premiumId == null || price == null || method == null)
        {
            throw new BadRequestParameterException("");
        }

        final var payment = Payment.builder()
                .withPaymentId(premiumId)
                .withAmount(BigDecimal.valueOf(price))
                .withMethod(method)
                .build();

        return service.payForPremium(payment);
    }

    public ResponseEntity<?> handleStripeWebhook(final String payload, final String sigHeader)
    {
        if (payload == null || sigHeader == null)
        {
            throw new BadRequestParameterException("");
        }

        service.handleStripeWebhook(payload, sigHeader);

        return ResponseEntity.ok().build();
    }
}
