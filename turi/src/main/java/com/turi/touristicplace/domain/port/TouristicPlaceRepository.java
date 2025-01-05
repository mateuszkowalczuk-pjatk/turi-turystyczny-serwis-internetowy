package com.turi.touristicplace.domain.port;

import com.turi.touristicplace.domain.model.TouristicPlace;

import java.time.LocalDate;
import java.util.List;

public interface TouristicPlaceRepository
{
    List<Object[]> findForSearch(final String query,
                                 final LocalDate dateFrom,
                                 final LocalDate dateTo,
                                 final Long limit,
                                 final Long touristicPlaceId,
                                 final Double rank);

    List<Object[]> findByStaysForSearch(final String query,
                                        final LocalDate dateFrom,
                                        final LocalDate dateTo,
                                        final Integer type,
                                        final Long limit,
                                        final Long touristicPlaceId,
                                        final Double rank);

    List<Object[]> findByAttractionsForSearch(final String query,
                                              final LocalDate dateFrom,
                                              final LocalDate dateTo,
                                              final Integer type,
                                              final Long limit,
                                              final Long touristicPlaceId,
                                              final Double rank);

    List<String> findForAutocomplete(final String query);

    TouristicPlace findById(final Long id);

    TouristicPlace findByPremiumId(final Long premiumId);

    TouristicPlace findByAddressId(final Long addressId);

    void insert(final TouristicPlace touristicPlace);

    void update(final Long id, final TouristicPlace touristicPlace);
}
