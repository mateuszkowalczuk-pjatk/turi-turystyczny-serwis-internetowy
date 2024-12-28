package com.turi.touristicplace.domain.port;

import com.turi.touristicplace.domain.model.GuaranteedService;

import java.util.List;

public interface GuaranteedServiceService
{
    List<GuaranteedService> getAllByTouristicPlaceId(final Long touristicPlaceId);

    GuaranteedService create(final GuaranteedService guaranteedService);

    void deleteById(final Long id);
}
