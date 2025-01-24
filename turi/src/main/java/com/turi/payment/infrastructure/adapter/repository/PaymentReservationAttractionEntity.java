package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.exception.InvalidPaymentReservationAttractionException;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paymentreservationattraction")
@Builder(setterPrefix = "with")
public final class PaymentReservationAttractionEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = 2806959470443044594L;

    @EmbeddedId
    private PaymentReservationAttractionId paymentReservationAttractionId;

    public static PaymentReservationAttractionEntity of(final Long paymentId, final Long reservationAttractionId)
    {
        if (!validation(paymentId, reservationAttractionId))
        {
            throw new InvalidPaymentReservationAttractionException();
        }

        final var paymentReservationAttractionId = PaymentReservationAttractionId.builder()
                .withPaymentId(paymentId)
                .withReservationAttractionId(reservationAttractionId)
                .build();

        return PaymentReservationAttractionEntity.builder()
                .withPaymentReservationAttractionId(paymentReservationAttractionId)
                .build();
    }

    private static boolean validation(final Long paymentId, final Long reservationAttractionId)
    {
        return paymentId != null && reservationAttractionId != null;
    }
}
