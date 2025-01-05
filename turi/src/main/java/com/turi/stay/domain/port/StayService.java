package com.turi.stay.domain.port;

import com.turi.stay.domain.model.Stay;
import com.turi.stay.domain.model.StayDto;
import com.turi.stay.domain.model.StayInformation;

import java.util.List;

public interface StayService
{
    List<String> completeNames(final String query);

    List<StayDto> getAllByTouristicPlaceId(final Long touristicPlaceId);

    StayDto getById(final Long id);

    Stay create(final StayDto stayDto);

    void createStayInformation(final StayInformation stayInformation);

    void update(final Long id, final Stay stay);

    void delete(final Long id);

    void deleteStayInformation(final Long stayInformationId);
}
