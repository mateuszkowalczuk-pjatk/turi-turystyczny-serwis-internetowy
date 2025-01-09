package com.turi.reservation.infrastructure.adapter.repository;

import com.turi.reservation.domain.exception.InvalidReservationAttractionException;
import com.turi.reservation.domain.model.ReservationAttraction;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservationattraction")
@Builder(setterPrefix = "with")
public final class ReservationAttractionEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -4346592440695089555L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationattractionid")
    private Long reservationAttractionId;

    @Column(name = "reservationid", nullable = false)
    private Long reservationId;

    @Column(name = "attractionid", nullable = false)
    private Long attractionId;

    @Column(name = "datefrom", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "dateto")
    private LocalDate dateTo;

    @Column(name = "hourfrom", nullable = false)
    private LocalTime hourFrom;

    @Column(name = "hourto", nullable = false)
    private LocalTime hourTo;

    @Column(name = "people")
    private Integer people;

    @Column(name = "items")
    private Integer items;

    @Column(name = "message")
    private String message;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "status", nullable = false)
    private int status;

    public static ReservationAttractionEntity of(final ReservationAttraction reservationAttraction)
    {
        if (!validation(reservationAttraction))
        {
            throw new InvalidReservationAttractionException();
        }

        return ReservationAttractionEntity.builder()
                .withReservationId(reservationAttraction.getReservationId())
                .withAttractionId(reservationAttraction.getAttractionId())
                .withDateFrom(reservationAttraction.getDateFrom())
                .withDateTo(reservationAttraction.getDateTo())
                .withHourFrom(reservationAttraction.getHourFrom())
                .withHourTo(reservationAttraction.getHourTo())
                .withPeople(reservationAttraction.getPeople())
                .withItems(reservationAttraction.getItems())
                .withMessage(reservationAttraction.getMessage())
                .withPrice(reservationAttraction.getPrice())
                .withStatus(reservationAttraction.getStatus().getValue())
                .build();
    }

    private static boolean validation(final ReservationAttraction reservationAttraction)
    {
        return reservationAttraction.getReservationId() != null
                && reservationAttraction.getAttractionId() != null
                && reservationAttraction.getDateFrom() != null
                && reservationAttraction.getHourFrom() != null
                && reservationAttraction.getHourTo() != null
                && reservationAttraction.getPrice() != null
                && reservationAttraction.getStatus() != null;
    }
}
