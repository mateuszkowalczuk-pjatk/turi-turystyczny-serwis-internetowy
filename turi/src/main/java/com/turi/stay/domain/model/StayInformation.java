package com.turi.stay.domain.model;

import com.turi.stay.infrastructure.adapter.repository.StayInformationEntity;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class StayInformation
{
    private Long stayInformationId;
    private Long stayId;
    private String information;

    public static StayInformation of(final StayInformationEntity entity)
    {
        return StayInformation.builder()
                .withStayId(entity.getStayId())
                .withInformation(entity.getInformation())
                .build();
    }
}
