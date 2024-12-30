package com.turi.touristicplace.domain.port;

import com.turi.attraction.domain.model.AttractionType;
import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;
import com.turi.touristicplace.domain.model.TouristicPlaceType;

import java.time.LocalDate;
import java.util.List;

public interface TouristicPlaceService
{
    List<TouristicPlace> getForSearch(final String query,
                                      final LocalDate dateFrom,
                                      final LocalDate dateTo,
                                      final Long limit,
                                      final Long cursor);

    List<TouristicPlace> getByStaysForSearch(final String query,
                                             final LocalDate dateFrom,
                                             final LocalDate dateTo,
                                             final TouristicPlaceType type,
                                             final Long limit,
                                             final Long cursor);

    List<TouristicPlace> getByAttractionsForSearch(final String query,
                                                   final LocalDate dateFrom,
                                                   final LocalDate dateTo,
                                                   final AttractionType type,
                                                   final Long limit,
                                                   final Long cursor);

    List<String> completeNames(final String query);

    TouristicPlace getById(final Long id);

    TouristicPlace getByPremiumId();

    Boolean isAddressExists(final String country,
                            final String city,
                            final String zipCode,
                            final String street,
                            final String buildingNumber,
                            final Integer apartmentNumber);

    List<GuaranteedService> getAllGuaranteedServices();

    List<GuaranteedService> getAllGuaranteedServicesByTouristicPlaceId(final Long touristicPlaceId);

    void create();

    GuaranteedService createGuaranteedService(final GuaranteedService guaranteedService);

    void updateDetails(final Long id, final TouristicPlace touristicPlace);

    void deleteGuaranteedService(final Long guaranteedServiceId);
}
