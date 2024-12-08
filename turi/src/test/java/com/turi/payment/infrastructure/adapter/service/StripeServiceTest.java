package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.model.PaymentStatus;
import com.turi.payment.domain.model.StripePayment;
import com.turi.payment.domain.port.StripeService;
import com.turi.testhelper.annotation.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@ServiceTest
class StripeServiceTest
{
    @Autowired
    private StripeService service;

    @Test
    void testStripePayment_GetByIntent()
    {

    }

    @Test
    void testStripePayment_GetByIntent_NotFound()
    {

    }

    @Test
    void testStripePayment_Checkout()
    {

    }

    @Test
    void testStripePayment_Checkout_Error()
    {

    }

    @Test
    void testStripePayment_Webhook_Succeeded()
    {

    }

    @Test
    void testStripePayment_Webhook_Failed()
    {

    }

    @Test
    void testStripePayment_Webhook_Completed()
    {

    }

    @Test
    void testStripePayment_Webhook_Error()
    {

    }

    private StripePayment mockStripePayment()
    {
        return StripePayment.builder()
                .withStripePaymentId(1L)
                .withIntent("sample_payment_intent")
                .withStatus(PaymentStatus.SUCCEEDED)
                .withPaymentDate(LocalDateTime.of(2024, 10, 10, 12, 0, 0))
                .build();
    }

    private StripePayment mockNewStripePayment()
    {
        return StripePayment.builder()
                .withStripePaymentId(2L)
                .withIntent("sample_other_payment_intent")
                .withStatus(PaymentStatus.SUCCEEDED)
                .withPaymentDate(LocalDateTime.of(2024, 10, 10, 14, 0, 0))
                .build();
    }
}
