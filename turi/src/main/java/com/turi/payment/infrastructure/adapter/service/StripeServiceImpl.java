package com.turi.payment.infrastructure.adapter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.exception.PaymentStripeException;
import com.turi.payment.domain.exception.PaymentWebhookException;
import com.turi.payment.domain.model.*;
import com.turi.payment.domain.port.StripePaymentRepository;
import com.turi.payment.domain.port.StripeService;
import com.turi.payment.infrastructure.config.StripeProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StripeServiceImpl implements StripeService
{
    private final ObjectMapper objectMapper;
    private final StripeProperties properties;
    private final StripePaymentRepository repository;

    @Override
    public StripePayment getByIntent(final String intent)
    {
        return repository.findByIntent(intent);
    }

    @Override
    public StripePaymentResponse checkout(final Payment payment, final PaymentName paymentName)
    {
        Stripe.apiKey = properties.getSecretKey();

        final var priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("PLN")
                .setUnitAmount((long) (payment.getAmount().doubleValue() * 100))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(paymentName.getName())
                                .build()
                )
                .build();

        final var lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(priceData)
                .build();

        final var params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(getSuccessUrl(paymentName))
                .setCancelUrl(getCancelUrl(paymentName))
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.valueOf(payment.getMethod().getName()))
                .addLineItem(lineItem)
                .build();

        try
        {
            final var session = Session.create(params);

            return StripePaymentResponse.builder()
                    .withStripeId(session.getId())
                    .withUrl(session.getUrl())
                    .withStatus(PaymentStatus.PENDING)
                    .build();
        }
        catch (final StripeException ex)
        {
            throw new PaymentStripeException(ex.getMessage());
        }
    }

    private String getSuccessUrl(final PaymentName paymentName)
    {
        return paymentName.equals(PaymentName.PREMIUM) ?
                properties.getPremiumSuccessUrl() :
                properties.getReservationSuccessUrl();
    }

    private String getCancelUrl(final PaymentName paymentName)
    {
        return paymentName.equals(PaymentName.PREMIUM) ?
                properties.getPremiumCancelUrl() :
                properties.getReservationCancelUrl();
    }

    @Override
    public StripePaymentResponse webhook(final String payload, final String sigHeader)
    {
        try
        {
            final var event = Webhook.constructEvent(payload, sigHeader, properties.getWebhookSecretKey());

            return switch (event.getType())
            {
                case "payment_intent.succeeded" ->
                {
                    createStripePayment(payload, PaymentStatus.SUCCEEDED);

                    yield null;
                }
                case "payment_intent.payment_failed" ->
                {
                    createStripePayment(payload, PaymentStatus.FAILED);

                    yield null;
                }
                case "checkout.session.completed" -> StripePaymentResponse.builder()
                        .withStripeId(getFromPayload(payload, "id"))
                        .withStripePaymentIntent(getFromPayload(payload, "payment_intent"))
                        .build();
                default -> null;
            };
        }
        catch (final Exception ex)
        {
            throw new PaymentWebhookException(ex.getMessage());
        }
    }

    private void createStripePayment(final String payload, final PaymentStatus status)
    {
        final var stripePayment = StripePayment.builder()
                .withIntent(getFromPayload(payload, "id"))
                .withStatus(status)
                .withPaymentDate(LocalDateTime.now())
                .build();

        repository.insert(stripePayment);
    }

    private String getFromPayload(final String payload, final String value)
    {
        try
        {
            final var eventNode = objectMapper.readTree(payload);

            return eventNode.path("data").path("object").path(value).asText();
        }
        catch (JsonProcessingException ex)
        {
            throw new BadRequestParameterException("Cannot get payment information from webhook process!");
        }
    }
}
