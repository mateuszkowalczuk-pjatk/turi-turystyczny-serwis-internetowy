package com.turi.payment.infrastructure.adapter.repository;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class PaymentReservationAttractionId implements Serializable
{
    @Serial
    private static final long serialVersionUID = -812715243565208345L;

    @Column(name = "paymentid", nullable = false)
    private Long paymentId;

    @Column(name = "reservationattractionid", nullable = false)
    private Long reservationAttractionId;
}
