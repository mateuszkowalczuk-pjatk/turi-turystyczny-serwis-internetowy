package com.turi.payment.infrastructure.adapter.interfaces;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/payment", produces = "application/json")
public class PaymentRestController
{
    private final PaymentFacade facade;

    @PostMapping("/webhook")
    public ResponseEntity<?> handleStripeWebhook(@RequestBody final String payload,
                                                 @RequestHeader("Stripe-Signature") final String sigHeader)
    {
        return facade.handleStripeWebhook(payload, sigHeader);
    }
}
