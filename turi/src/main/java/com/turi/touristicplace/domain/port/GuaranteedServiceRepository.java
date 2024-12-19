package com.turi.touristicplace.domain.port;

import com.turi.touristicplace.domain.model.GuaranteedService;

import java.util.List;

public interface GuaranteedServiceRepository
{
    List<GuaranteedService> findAllByTouristicPlaceId(final Long touristicPlaceId);
}
