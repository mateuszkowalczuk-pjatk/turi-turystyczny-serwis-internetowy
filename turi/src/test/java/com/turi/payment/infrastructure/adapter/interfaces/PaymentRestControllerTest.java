package com.turi.payment.infrastructure.adapter.interfaces;

import com.turi.infrastructure.rest.ErrorCode;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RestControllerTest
class PaymentRestControllerTest extends AbstractRestControllerIntegrationTest
{
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
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/payment/webhook")
                .queryParam("payload", "sample-webhook-payload")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testPayment_HandleStripeWebhook_WithoutRequiredSigHeaderField()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/payment/webhook")
                .queryParam("sigHeader", "sample-webhook-sigHeader")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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
