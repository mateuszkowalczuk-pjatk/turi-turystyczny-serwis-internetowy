package com.turi.offer.infrastructure.adapter.repository;

import com.turi.offer.domain.exception.InvalidFavouriteException;
import com.turi.offer.domain.model.Favourite;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favourite")
@Builder(setterPrefix = "with")
public final class FavouriteEntity implements Serializable
{
    @Serial
    private static final long serialVersionUID = -6388695246335004554L;

    @EmbeddedId
    private FavouriteId favouriteId;

    public static FavouriteEntity of(final Favourite favourite)
    {
        if (!validation(favourite))
        {
            throw new InvalidFavouriteException();
        }

        final var favouriteId = FavouriteId.builder()
                .withAccountId(favourite.getAccountId())
                .withTouristicPlaceId(favourite.getTouristicPlaceId())
                .build();

        return FavouriteEntity.builder()
                .withFavouriteId(favouriteId)
                .build();
    }

    private static boolean validation(final Favourite favourite)
    {
        return favourite.getAccountId() != null && favourite.getTouristicPlaceId() != null;
    }
}
