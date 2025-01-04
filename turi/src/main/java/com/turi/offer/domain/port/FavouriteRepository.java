package com.turi.offer.domain.port;

import com.turi.offer.domain.model.Favourite;

import java.util.List;

public interface FavouriteRepository
{
    List<Favourite> findAllByAccount(final Long accountId);

    Favourite findByAccountIdAndTouristicPlaceId(final Long accountId, final Long touristicPlaceId);

    void insertForAccount(final Long accountId, final Long touristicPlaceId);

    void deleteForAccount(final Long accountId, final Long touristicPlaceId);
}
