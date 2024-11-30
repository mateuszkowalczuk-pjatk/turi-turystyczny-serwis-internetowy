package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.port.PaymentRepository;
import com.turi.payment.domain.port.PaymentService;
//import com.turi.payment.domain.port.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService
{
//    private final StripeService stripeService;
    private final PaymentRepository repository;

    @Override
    public Payment getById(final Long id)
    {
        return null;
    }

    @Override
    public String payForPremium()
    {
//        zbudowanie obiektu payment
//        uruchomienie stripe
//        zapisanie do bazy payemnt
        return "";
    }

    @Override
    public void handleStripeWebhook(final String payload, final String sigHeader)
    {
// uruchomienie webhoot
//        aktualizacja w bazie
    }
}
