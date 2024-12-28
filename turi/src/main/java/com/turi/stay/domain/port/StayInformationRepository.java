package com.turi.stay.domain.port;

import com.turi.stay.domain.model.StayInformation;

import java.util.List;

public interface StayInformationRepository
{
    StayInformation findById(final Long id);

    List<StayInformation> findAllByStayId(final Long stayId);

    void insert(final StayInformation stayInformation);

    void deleteById(final Long id);

    void deleteAllByStayId(final Long stayId);
}
