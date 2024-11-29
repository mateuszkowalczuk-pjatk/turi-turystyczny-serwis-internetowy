package com.turi.payment.domain.model;

import com.turi.payment.infrastructure.adapter.repository.PaymentEntity;
import lombok.*;

import java.time.LocalDate;

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
    private Long stripeId;
    private Double amount;
    private LocalDate paymentDate;
    private PaymentMethod method;
    private PaymentStatus status;

    public static Payment of(final PaymentEntity entity)
    {
        return Payment.builder()
                .withPaymentId(entity.getPaymentId())
                .withPremiumId(entity.getPremiumId())
                .withStripeId(entity.getStripeId())
                .withAmount(entity.getAmount())
                .withPaymentDate(entity.getPaymentDate())
                .withMethod(PaymentMethod.fromValue(entity.getMethod()))
                .withStatus(PaymentStatus.fromValue(entity.getStatus()))
                .build();
    }
}
