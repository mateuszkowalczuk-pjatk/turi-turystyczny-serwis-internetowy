package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.exception.InvalidStripePaymentException;
import com.turi.payment.domain.model.StripePayment;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.turi.payment.domain.model.PaymentStatus.getValueOrDefault;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stripepayment")
@Builder(setterPrefix = "with")
public final class StripePaymentEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1548749380095504264L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stripepaymentid")
    private Long stripePaymentId;

    @Column(name = "intent", nullable = false, unique = true)
    private String intent;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "paymentdate", nullable = false)
    private LocalDateTime paymentDate;

    public static StripePaymentEntity of(final StripePayment stripePayment)
    {
        if (!validation(stripePayment))
        {
            throw new InvalidStripePaymentException();
        }

        return StripePaymentEntity.builder()
                .withIntent(stripePayment.getIntent())
                .withStatus(getValueOrDefault(stripePayment.getStatus()))
                .withPaymentDate(stripePayment.getPaymentDate())
                .build();
    }

    private static boolean validation(final StripePayment stripePayment)
    {
        return stripePayment.getIntent() != null
                && stripePayment.getStatus() != null
                && stripePayment.getPaymentDate() != null;
    }
}
