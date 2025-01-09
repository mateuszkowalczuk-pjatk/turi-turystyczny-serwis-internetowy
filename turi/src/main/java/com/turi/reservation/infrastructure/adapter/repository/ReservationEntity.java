package com.turi.reservation.infrastructure.adapter.repository;

import com.turi.reservation.domain.exception.InvalidReservationException;
import com.turi.reservation.domain.model.Reservation;
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
@Table(name = "reservation")
@Builder(setterPrefix = "with")
public final class ReservationEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -1613342193759080746L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationid")
    private Long reservationId;

    @Column(name = "stayid", nullable = false)
    private Long stayId;

    @Column(name = "accountid", nullable = false)
    private Long accountId;

    @Column(name = "datefrom", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "dateto", nullable = false)
    private LocalDate dateTo;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "checkintime", nullable = false)
    private LocalTime checkInTime;

    @Column(name = "request")
    private String request;

    @Column(name = "guestname", length = 50)
    private String guestName;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "opinion")
    private String opinion;

    @Column(name = "status", nullable = false)
    private int status;

    public static ReservationEntity of(final Reservation reservation)
    {
        if (!validation(reservation))
        {
            throw new InvalidReservationException();
        }

        return ReservationEntity.builder()
                .withStayId(reservation.getStayId())
                .withAccountId(reservation.getAccountId())
                .withDateFrom(reservation.getDateFrom())
                .withDateTo(reservation.getDateTo())
                .withPrice(reservation.getPrice())
                .withCheckInTime(reservation.getCheckInTime())
                .withRequest(reservation.getRequest())
                .withGuestName(reservation.getGuestName())
                .withRating(reservation.getRating())
                .withOpinion(reservation.getOpinion())
                .withStatus(reservation.getStatus().getValue())
                .build();
    }

    private static boolean validation(final Reservation reservation)
    {
        return reservation.getStayId() != null
                && reservation.getAccountId() != null
                && reservation.getDateFrom() != null
                && reservation.getDateTo() != null
                && reservation.getCheckInTime() != null
                && reservation.getStatus() != null;
    }
}
