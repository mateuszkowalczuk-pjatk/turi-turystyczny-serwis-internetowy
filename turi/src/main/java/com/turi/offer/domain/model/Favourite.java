package com.turi.offer.domain.model;

import com.turi.offer.infrastructure.adapter.repository.FavouriteEntity;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class Favourite
{
    private Long accountId;
    private Long touristicPlaceId;

    public static Favourite of(final FavouriteEntity entity)
    {
        return Favourite.builder()
                .withAccountId(entity.getFavouriteId().getAccountId())
                .withTouristicPlaceId(entity.getFavouriteId().getTouristicPlaceId())
                .build();
    }
}
