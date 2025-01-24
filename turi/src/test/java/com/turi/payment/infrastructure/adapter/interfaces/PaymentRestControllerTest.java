package com.turi.payment.infrastructure.adapter.interfaces;

import com.turi.infrastructure.rest.ErrorCode;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RestControllerTest
class PaymentRestControllerTest extends AbstractRestControllerIntegrationTest
{

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
}
