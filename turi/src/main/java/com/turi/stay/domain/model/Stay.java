package com.turi.stay.domain.model;

import com.turi.stay.infrastructure.adapter.repository.StayEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Stay
{
    private Long stayId;
    private Long touristicPlaceId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer peopleNumber;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public static Stay of(final StayEntity entity)
    {
        return Stay.builder()
                .withStayId(entity.getStayId())
                .withTouristicPlaceId(entity.getTouristicPlaceId())
                .withName(entity.getName())
                .withDescription(entity.getDescription())
                .withPrice(entity.getPrice())
                .withPeopleNumber(entity.getPeopleNumber())
                .withDateFrom(entity.getDateFrom())
                .withDateTo(entity.getDateTo())
                .build();
    }
}
