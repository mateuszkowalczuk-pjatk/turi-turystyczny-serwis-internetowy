package com.turi.reservation.domain.model;

import com.turi.reservation.infrastructure.adapter.repository.ReservationAttractionEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class ReservationAttraction
{
    private Long reservationAttractionId;
    private Long reservationId;
    private Long attractionId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalTime hourFrom;
    private LocalTime hourTo;
    private Integer people;
    private Integer items;
    private String message;
    private Double price;
    private LocalDateTime modifyDate;
    private ReservationStatus status;

    public static ReservationAttraction of(final ReservationAttractionEntity entity)
    {
        return ReservationAttraction.builder()
                .withReservationAttractionId(entity.getReservationAttractionId())
                .withReservationId(entity.getReservationId())
                .withAttractionId(entity.getAttractionId())
                .withDateFrom(entity.getDateFrom())
                .withDateTo(entity.getDateTo())
                .withHourFrom(entity.getHourFrom())
                .withHourTo(entity.getHourTo())
                .withPeople(entity.getPeople())
                .withItems(entity.getItems())
                .withMessage(entity.getMessage())
                .withPrice(entity.getPrice())
                .withModifyDate(entity.getModifyDate())
                .withStatus(ReservationStatus.fromValue(entity.getStatus()))
                .build();
    }
}
