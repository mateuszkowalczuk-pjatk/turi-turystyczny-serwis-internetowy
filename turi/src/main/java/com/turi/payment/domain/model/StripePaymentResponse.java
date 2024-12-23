package com.turi.payment.domain.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class StripePaymentResponse
{
    private String stripeId;
    private String stripePaymentIntent;
    private String url;
    private PaymentStatus status;
}
