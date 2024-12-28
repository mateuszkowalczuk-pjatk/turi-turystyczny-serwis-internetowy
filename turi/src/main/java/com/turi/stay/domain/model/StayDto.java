package com.turi.stay.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class StayDto
{
    private Long stayId;
    private Long touristicPlaceId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer peopleNumber;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private List<StayInformation> stayInformations;
}
