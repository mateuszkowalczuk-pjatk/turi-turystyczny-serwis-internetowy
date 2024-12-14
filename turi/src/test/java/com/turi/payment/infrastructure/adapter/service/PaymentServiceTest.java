package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.payment.domain.port.PaymentService;
import com.turi.testhelper.annotation.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class PaymentServiceTest
{
    @Autowired
    private PaymentService service;

    @Test
    void testPayment_IsPaymentForPremiumSucceeded_PaymentSucceeded()
    {
        final var result = service.isPaymentForPremiumSucceeded(mockPayment().getPremiumId());

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testPayment_IsPaymentForPremiumSucceeded_PaymentNotFound()
    {
        final var result = service.isPaymentForPremiumSucceeded(mockNewPayment().getPremiumId());

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testPayment_IsPaymentForPremiumSucceeded_StripePaymentNotFound()
    {

    }

    @Test
    void testPayment_IsPaymentForPremiumSucceeded_PaymentFailed()
    {

    }

    @Test
    void testPayment_IsPaymentForPremiumSucceeded_PaymentCompleted()
    {

    }

    @Test
    void testPayment_PayForPremium()
    {

    }

    @Test
    void testPayment_PayForPremium_Error()
    {

    }

    @Test
    void testPayment_HandleStripeWebhook_Succeeded()
    {

    }

    @Test
    void testPayment_HandleStripeWebhook_Failed()
    {

    }

    @Test
    void testPayment_HandleStripeWebhook_Completed()
    {

    }

    @Test
    void testPayment_HandleStripeWebhook_Error()
    {

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

    private Payment mockNewPayment()
    {
        return Payment.builder()
                .withPaymentId(2L)
                .withPremiumId(2L)
                .withStripeId("sample_stripe_other_session_id")
                .withStripePaymentIntent("sample_other_payment_intent")
                .withAmount(BigDecimal.valueOf(100.55))
                .withPaymentDate(LocalDateTime.of(2024, 10, 10, 12, 0, 0))
                .withMethod(PaymentMethod.CARD)
                .withStatus(PaymentStatus.SUCCEEDED)
                .build();
    }
}
