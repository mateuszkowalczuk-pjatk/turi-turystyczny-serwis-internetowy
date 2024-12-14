package com.turi.payment.domain.model;

import com.turi.payment.infrastructure.adapter.repository.StripePaymentEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class StripePayment
{
    private Long stripePaymentId;
    private String intent;
    private LocalDateTime paymentDate;
    private PaymentStatus status;

    public static StripePayment of(final StripePaymentEntity entity)
    {
        return StripePayment.builder()
                .withStripePaymentId(entity.getStripePaymentId())
                .withIntent(entity.getIntent())
                .withStatus(PaymentStatus.fromValue(entity.getStatus()))
                .withPaymentDate(entity.getPaymentDate())
                .build();
    }
}
