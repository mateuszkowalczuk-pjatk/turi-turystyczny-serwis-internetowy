package com.turi.payment.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.testhelper.annotation.RestControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RestControllerTest
class PaymentFacadeTest
{
    @Autowired(required = false)
    private PaymentFacade facade;

    @Test
    void testPayment_IsPaymentForPremiumSucceeded_WithoutRequiredPremiumIdField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isPaymentForPremiumSucceeded(null));
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
    void testPayment_HandleStripeWebhook_WithoutRequiredPayloadField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.handleStripeWebhook(null, "sample-webhook-sigHeader"));
    }

    @Test
    void testPayment_HandleStripeWebhook_WithoutRequiredSigHeaderField()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.handleStripeWebhook("sample-webhook-payload", null));
    }
}
