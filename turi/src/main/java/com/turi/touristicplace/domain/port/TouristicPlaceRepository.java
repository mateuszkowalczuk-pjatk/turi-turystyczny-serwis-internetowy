package com.turi.touristicplace.domain.port;

import com.turi.touristicplace.domain.model.TouristicPlace;

public interface TouristicPlaceRepository
{
    TouristicPlace findById(final Long id);

    TouristicPlace findByPremiumId(final Long premiumId);

    TouristicPlace findByAddressId(final Long addressId);

    void insert(final TouristicPlace touristicPlace);

    void update(final Long id, final TouristicPlace touristicPlace);
}
