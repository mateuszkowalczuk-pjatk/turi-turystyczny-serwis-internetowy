package com.turi.offer.domain.port;

import com.turi.offer.domain.model.Offer;

import java.util.List;

public interface FavouriteService
{
    List<Offer> getAllByAccount(final Long accountId);

    Boolean isOfferForAccount(final Long accountId, final Long touristicPlaceId);

    void create(final Long accountId, final Long touristicPlaceId);

    void delete(final Long accountId, final Long touristicPlaceId);
}
