package com.turi.attraction.domain.model;

import com.turi.attraction.infrastructure.adapter.repository.AttractionEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Attraction
{
    private Long attractionId;
    private Long touristicPlaceId;
    private AttractionType attractionType;
    private String name;
    private String description;
    private BigDecimal price;
    private PriceType priceType;
    private Boolean prepayment;
    private Integer cancelReservationDays;
    private Integer maxPeopleNumber;
    private Integer maxItems;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalTime hourFrom;
    private LocalTime hourTo;
    private Integer daysReservationBefore;

    public static Attraction of(final AttractionEntity entity)
    {
        return Attraction.builder()
                .withAttractionId(entity.getAttractionId())
                .withTouristicPlaceId(entity.getTouristicPlaceId())
                .withAttractionType(AttractionType.fromValue(entity.getAttractionType()))
                .withName(entity.getName())
                .withDescription(entity.getDescription())
                .withPrice(entity.getPrice())
                .withPriceType(PriceType.fromValue(entity.getPriceType()))
                .withPrepayment(entity.getPrepayment())
                .withCancelReservationDays(entity.getCancelReservationDays())
                .withMaxPeopleNumber(entity.getMaxPeopleNumber())
                .withMaxItems(entity.getMaxItems())
                .withDateFrom(entity.getDateFrom())
                .withDateTo(entity.getDateTo())
                .withHourFrom(entity.getHourFrom())
                .withHourTo(entity.getHourTo())
                .withDaysReservationBefore(entity.getDaysReservationBefore())
                .build();
    }
}
