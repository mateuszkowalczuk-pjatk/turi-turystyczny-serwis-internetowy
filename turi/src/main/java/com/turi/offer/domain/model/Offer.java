package com.turi.offer.domain.model;

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
public final class Offer
{
    private TouristicPlace touristicPlace;
    private List<GuaranteedService> guaranteedServices;
    private List<StayDto> stays;
    private List<Attraction> attractions;
}
