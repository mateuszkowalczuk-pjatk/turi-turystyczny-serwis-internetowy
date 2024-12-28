package com.turi.touristicplace.domain.model;

import com.turi.touristicplace.infrastructure.adapter.repository.GuaranteedServiceEntity;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class GuaranteedService
{
    private Long guaranteedServiceId;
    private Long touristicPlaceId;
    private String service;

    public static GuaranteedService of(final GuaranteedServiceEntity entity)
    {
        return GuaranteedService.builder()
                .withGuaranteedServiceId(entity.getGuaranteedServiceId())
                .withTouristicPlaceId(entity.getTouristicPlaceId())
                .withService(entity.getService())
                .build();
    }
}
