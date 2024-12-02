package com.turi.payment.infrastructure.adapter.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.turi.infrastructure.config.StripeProperties;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.exception.PaymentWebhookException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.payment.domain.model.PaymentStripeResponse;
import com.turi.payment.domain.port.StripeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StripeServiceImpl implements StripeService
{
    private final StripeProperties properties;

    @Override
    public PaymentStripeResponse checkout(final Payment payment)
    {
        Stripe.apiKey = properties.getSecretKey();

        final var priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("PLN")
                        .setUnitAmount((long) (payment.getAmount().doubleValue() * 100))
                        .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Premium Account Subscription")
                                        .build()
                        )
                        .build();

        final var lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        final var params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:3000/success?stripeId={CHECKOUT_SESSION_ID}")
                        .setCancelUrl("http://localhost:3000/cancel")
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.valueOf(payment.getMethod().getName()))
                        .addLineItem(lineItem)
                        .build();

        try
        {
            final var session = Session.create(params);

            return PaymentStripeResponse.builder()
                    .withStripeId(Long.valueOf(session.getId()))
                    .withUrl(session.getUrl())
                    .withStatus(PaymentStatus.PENDING)
                    .build();
        }
        catch (final StripeException ex)
        {
            throw new RuntimeException("Error creating Stripe session: " + ex.getMessage(), ex);
        }
    }

    @Override
    public PaymentStripeResponse webhook(final String payload, final String sigHeader)
    {
        try
        {
            final var event = Webhook.constructEvent(payload, sigHeader, properties.getWebhookSecretKey());

            return switch (event.getType()) {
                case "payment_intent.succeeded" -> PaymentStripeResponse.builder()
                        .withStripeId(getStripeId(event))
                        .withStatus(PaymentStatus.SUCCEEDED)
                        .build();
                case "payment_intent.payment_failed" -> PaymentStripeResponse.builder()
                        .withStripeId(getStripeId(event))
                        .withStatus(PaymentStatus.FAILED)
                        .build();
                default -> null;
            };
        }
        catch (final Exception ex)
        {
            throw new PaymentWebhookException();
        }
    }

    private Long getStripeId(final Event event)
    {
        return event.getDataObjectDeserializer()
                .getObject()
                .map(object -> Long.valueOf(((PaymentIntent) object).getId()))
                .orElseThrow(() -> new BadRequestParameterException("Invalid webhook data!"));
    }
}
