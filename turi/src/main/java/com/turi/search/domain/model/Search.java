package com.turi.search.domain.model;

import com.turi.attraction.domain.model.Attraction;
import com.turi.stay.domain.model.StayDto;
import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;
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
    private List<SearchTouristicPlace> searchTouristicPlaces;
    private Long touristicPlaceId;
    private Double rank;

    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(setterPrefix = "with")
    public static final class SearchTouristicPlace
    {
        private TouristicPlace touristicPlace;
        private List<GuaranteedService> guaranteedServices;
        private List<StayDto> stays;
        private List<Attraction> attractions;
    }
}
