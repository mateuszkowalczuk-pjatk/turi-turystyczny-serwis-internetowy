package com.turi.stay.domain.port;

import com.turi.stay.domain.model.Stay;
import com.turi.stay.domain.model.StayDto;

import java.util.List;

public interface StayService
{
    List<StayDto> getAllByTouristicPlaceId(final Long touristicPlaceId);

    StayDto getById(final Long id);

    void create(final StayDto stayDto);

    void update(final Long id, final Stay stay);

    void delete(final Long id);
}
