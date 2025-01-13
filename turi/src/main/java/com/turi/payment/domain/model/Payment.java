package com.turi.payment.domain.model;

import com.turi.payment.infrastructure.adapter.repository.PaymentEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Payment
{
    private Long paymentId;
    private Long premiumId;
    private Long reservationId;
    private String stripeId;
    private String stripePaymentIntent;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentMethod method;
    private PaymentStatus status;

    public static Payment of(final PaymentEntity entity)
    {
        return Payment.builder()
                .withPaymentId(entity.getPaymentId())
                .withReservationId(entity.getReservationId())
                .withPremiumId(entity.getPremiumId())
                .withStripeId(entity.getStripeId())
                .withStripePaymentIntent(entity.getStripePaymentIntent())
                .withAmount(entity.getAmount())
                .withPaymentDate(entity.getPaymentDate())
                .withMethod(PaymentMethod.fromValue(entity.getMethod()))
                .withStatus(PaymentStatus.fromValue(entity.getStatus()))
                .build();
    }
}
