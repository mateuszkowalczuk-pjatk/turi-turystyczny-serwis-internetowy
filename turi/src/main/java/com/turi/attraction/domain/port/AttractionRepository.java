package com.turi.attraction.domain.port;

import com.turi.attraction.domain.model.Attraction;

import java.util.List;

public interface AttractionRepository
{
    List<String> findForAutocomplete(final String query);

    List<Attraction> findAllByTouristicPlaceId(final Long touristicPlaceId);

    Attraction findById(final Long id);

    Long insert(final Attraction attraction);

    void update(final Long id, final Attraction attraction);

    void delete(final Long id);
}
