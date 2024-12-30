package com.turi.stay.domain.port;

import com.turi.stay.domain.model.Stay;

import java.util.List;

public interface StayRepository
{
    List<String> findForAutocomplete(final String query);

    List<Stay> findAllByTouristicPlaceId(final Long touristicPlaceId);

    Stay findById(final Long id);

    Long insert(final Stay stay);

    void update(final Long id, final Stay stay);

    void delete(final Long id);
}
