package com.turi.reservation.domain.model;

import com.turi.reservation.infrastructure.adapter.repository.ReservationEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String request;
    private Double rating;
    private String opinion;
    private LocalDateTime modifyDate;
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
                .withRequest(entity.getRequest())
                .withRating(entity.getRating())
                .withOpinion(entity.getOpinion())
                .withModifyDate(entity.getModifyDate())
                .withStatus(ReservationStatus.fromValue(entity.getStatus()))
                .build();
    }
}
