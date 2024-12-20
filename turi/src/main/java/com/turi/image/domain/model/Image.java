package com.turi.image.domain.model;

import com.turi.image.infrastructure.adapter.repository.ImageEntity;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class Image
{
    private Long imageId;
    private Long accountId;
    private Long touristicPlaceId;
    private Long stayId;
    private Long attractionId;
    private String path;

    public static Image of(final ImageEntity entity)
    {
        return Image.builder()
                .withImageId(entity.getImageId())
                .withAccountId(entity.getAccountId())
                .withTouristicPlaceId(entity.getTouristicPlaceId())
                .withStayId(entity.getStayId())
                .withAttractionId(entity.getAttractionId())
                .withPath(entity.getPath())
                .build();
    }
}
