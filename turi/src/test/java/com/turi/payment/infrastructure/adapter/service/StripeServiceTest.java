package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.model.*;
import com.turi.payment.domain.port.StripeService;
import com.turi.testhelper.annotation.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ServiceTest
class StripeServiceTest
{
    @Autowired
    private StripeService service;

    @Test
    void testStripePayment_GetByIntent()
    {
        final var stripePayment = mockStripePayment();

        final var result = service.getByIntent(stripePayment.getIntent());

        assertNotNull(result);
        assertThat(result).isEqualTo(stripePayment);
    }

    @Test
    void testStripePayment_GetByIntent_NotFound()
    {
        assertNull(service.getByIntent(mockNewStripePayment().getIntent()));
    }

    @Test
    void testStripePayment_Checkout()
    {
        final var payment = mockPayment();

        final var result = service.checkout(payment, PaymentName.PREMIUM);

        assertNotNull(result);
        assertNotNull(result.getStripeId());
        assertNotNull(result.getUrl());
        assertThat(result.getStatus()).isEqualTo(PaymentStatus.PENDING);
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

    private Payment mockPayment()
    {
        return Payment.builder()
                .withPaymentId(1L)
                .withPremiumId(1L)
                .withStripeId("sample_stripe_session_id")
                .withStripePaymentIntent("sample_payment_intent")
                .withAmount(BigDecimal.valueOf(100.25))
                .withPaymentDate(LocalDateTime.of(2024, 10, 10, 12, 0, 0))
                .withMethod(PaymentMethod.CARD)
                .withStatus(PaymentStatus.SUCCEEDED)
                .build();
    }
}
