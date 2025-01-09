package com.turi.reservation.domain.model;

import com.turi.reservation.infrastructure.adapter.repository.ReservationEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Reservation
{
    private Long reservationId;
    private Long stayId;
    private Long accountId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Double price;
    private LocalTime checkInTime;
    private String request;
    private String guestName;
    private Double rating;
    private String opinion;
    private ReservationStatus status;

    public static Reservation of(final ReservationEntity entity)
    {
        return Reservation.builder()
                .withReservationId(entity.getReservationId())
                .withStayId(entity.getStayId())
                .withAccountId(entity.getAccountId())
                .withDateFrom(entity.getDateFrom())
                .withDateTo(entity.getDateTo())
                .withPrice(entity.getPrice())
                .withCheckInTime(entity.getCheckInTime())
                .withRequest(entity.getRequest())
                .withGuestName(entity.getGuestName())
                .withRating(entity.getRating())
                .withOpinion(entity.getOpinion())
                .withStatus(ReservationStatus.fromValue(entity.getStatus()))
                .build();
    }
}
