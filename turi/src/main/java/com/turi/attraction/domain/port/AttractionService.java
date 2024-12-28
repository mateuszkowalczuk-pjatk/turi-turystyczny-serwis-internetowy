package com.turi.attraction.domain.port;

import com.turi.attraction.domain.model.Attraction;

import java.util.List;

public interface AttractionService
{
    List<Attraction> getAllByTouristicPlaceId(final Long touristicPlaceId);

    Attraction getById(final Long id);

    Attraction create(final Attraction attraction);

    void update(final Long id, final Attraction attraction);

    void delete(final Long id);
}
