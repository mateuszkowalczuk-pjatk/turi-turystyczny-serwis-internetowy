package com.turi.payment.domain.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class PaymentStripeResponse
{
    private Long stripeId;
    private String url;
    private PaymentStatus status;
}
