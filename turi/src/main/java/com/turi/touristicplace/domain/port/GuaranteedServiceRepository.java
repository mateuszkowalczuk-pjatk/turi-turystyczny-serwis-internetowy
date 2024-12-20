package com.turi.touristicplace.domain.port;

import com.turi.touristicplace.domain.model.GuaranteedService;

import java.util.List;

public interface GuaranteedServiceRepository
{
    GuaranteedService findById(final Long id);

    List<GuaranteedService> findAllByTouristicPlaceId(final Long touristicPlaceId);

    GuaranteedService findByTouristicPlaceIdAndService(final Long touristicPlaceId, final String service);

    void insert(final GuaranteedService guaranteedService);

    void deleteById(final Long id);
}
