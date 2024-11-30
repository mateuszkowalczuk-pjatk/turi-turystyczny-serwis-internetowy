package com.turi.payment.infrastructure.adapter.interfaces;

import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.port.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentFacade
{
    private final PaymentService service;

    public Boolean isPaymentForPremiumSucceeded(final Long accountId)
    {
        return true;
    }

    public String payForPremium(final double price, final PaymentMethod method)
    {
        return service.payForPremium(); // Zwracamy URL do przekierowania na stronę płatności ktore ma zwrocic endpoint w premium
    }

    public ResponseEntity<?> handleStripeWebhook(final String payload, final String sigHeader)
    {
        return ResponseEntity.ok().build();
    }
}
