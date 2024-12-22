package com.turi.touristicplace.domain.port;

import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;

import java.util.List;

public interface TouristicPlaceService
{
    TouristicPlace getById(final Long id);

    TouristicPlace getByPremiumId();

    Boolean isAddressExists(final String country,
                            final String city,
                            final String zipCode,
                            final String street,
                            final String buildingNumber,
                            final Integer apartmentNumber);

    List<GuaranteedService> getAllGuaranteedServices();

    void create();

    void createGuaranteedService(final GuaranteedService guaranteedService);

    void updateDetails(final Long id, final TouristicPlace touristicPlace);

    void deleteGuaranteedService(final Long guaranteedServiceId);
}
