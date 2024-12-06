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

    public Boolean isPaymentForPremiumSucceeded(final Long premiumId)
    {
        return service.isPaymentForPremiumSucceeded(premiumId);
    }

    public PaymentStripeResponse payForPremium(final Long premiumId, final Double price, final PaymentMethod method)
    {
        if (premiumId == null || price == null || method == null)
        {
            throw new BadRequestParameterException("Parameters premiumId, price and method are required.");
        }

        final var payment = Payment.builder()
                .withPremiumId(premiumId)
                .withAmount(BigDecimal.valueOf(price))
                .withMethod(method)
                .build();

        return service.payForPremium(payment);
    }

    public ResponseEntity<?> handleStripeWebhook(final String payload, final String sigHeader)
    {
        if (payload == null || sigHeader == null)
        {
            throw new BadRequestParameterException("Parameters payload and sigHeader are required.");
        }

        service.handleStripeWebhook(payload, sigHeader);

        return ResponseEntity.ok().build();
    }
}
