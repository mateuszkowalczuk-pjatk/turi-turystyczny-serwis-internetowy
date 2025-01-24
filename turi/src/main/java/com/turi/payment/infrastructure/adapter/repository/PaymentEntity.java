package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.exception.InvalidPaymentException;
import com.turi.payment.domain.model.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
@Builder(setterPrefix = "with")
public final class PaymentEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -1981599496967151398L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentid")
    private Long paymentId;

    @Column(name = "premiumid")
    private Long premiumId;

    @Column(name = "reservationid")
    private Long reservationId;

    @Column(name = "stripeid", nullable = false, unique = true)
    private String stripeId;

    @Column(name = "stripepaymentintent", unique = true)
    private String stripePaymentIntent;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "paymentdate", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "method", nullable = false)
    private int method;

    @Column(name = "status", nullable = false)
    private int status;

    public static PaymentEntity of(final Payment payment)
    {
        if (!validation(payment))
        {
            throw new InvalidPaymentException();
        }

        return PaymentEntity.builder()
                .withPremiumId(payment.getPremiumId())
                .withReservationId(payment.getReservationId())
                .withStripeId(payment.getStripeId())
                .withStripePaymentIntent(payment.getStripePaymentIntent())
                .withAmount(payment.getAmount())
                .withPaymentDate(payment.getPaymentDate())
                .withMethod(com.turi.payment.domain.model.PaymentMethod.getValueOrDefault(payment.getMethod()))
                .withStatus(com.turi.payment.domain.model.PaymentStatus.getValueOrDefault(payment.getStatus()))
                .build();
    }

    private static boolean validation(final Payment payment)
    {
        return (payment.getPremiumId() != null || payment.getReservationId() != null)
                && payment.getStripeId() != null
                && payment.getAmount() != null
                && payment.getPaymentDate() != null
                && payment.getMethod() != null
                && payment.getStatus() != null;
    }
}
