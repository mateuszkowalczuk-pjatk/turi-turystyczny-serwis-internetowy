package com.turi.offer.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Search
{
    private List<Offer> offers;
    private Long touristicPlaceId;
    private Double rank;
}
