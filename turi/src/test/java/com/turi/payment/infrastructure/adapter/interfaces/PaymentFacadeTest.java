package com.turi.payment.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.testhelper.annotation.RestControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RestControllerTest
class PaymentFacadeTest
{
    @Autowired(required = false)
    private PaymentFacade facade;

    @Test
    void testPayment_IsPaymentForPremiumSucceeded_PaymentSucceeded()
    {
        final var result = facade.isPaymentForPremiumSucceeded(mockPayment().getPremiumId());

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testPayment_IsPaymentForPremiumSucceeded_PaymentNotFound()
    {
        final var result = facade.isPaymentForPremiumSucceeded(mockNewPayment().getPremiumId());

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
    void testPayment_IsPaymentForPremiumSucceeded_WithoutRequiredPremiumIdField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isPaymentForPremiumSucceeded(null));
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
    void testPayment_PayForPremium_WithoutRequiredPremiumIdField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.payForPremium(null, 100.0, PaymentMethod.CARD));
    }

    @Test
    void testPayment_PayForPremium_WithoutRequiredPriceField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.payForPremium(1L, null, PaymentMethod.CARD));
    }

    @Test
    void testPayment_PayForPremium_WithoutRequiredMethodField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.payForPremium(1L, 100.0, null));
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

    @Test
    void testPayment_HandleStripeWebhook_WithoutRequiredPayloadField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.handleStripeWebhook(null, "sample-webhook-sigHeader"));
    }

    @Test
    void testPayment_HandleStripeWebhook_WithoutRequiredSigHeaderField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.handleStripeWebhook("sample-webhook-payload", null));
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
