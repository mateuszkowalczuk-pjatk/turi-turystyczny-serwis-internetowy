package com.turi.stay.domain.port;

import com.turi.stay.domain.model.StayInformation;

import java.util.List;

public interface StayInformationService
{
    List<StayInformation> getAllByStayId(final Long stayId);

    void create(final StayInformation stayInformation);

    void deleteById(final Long id);

    void deleteAllByStayId(final Long stayId);
}
