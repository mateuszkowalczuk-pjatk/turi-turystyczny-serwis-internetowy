package com.turi.search.domain.port;

import com.turi.attraction.domain.model.AttractionType;
import com.turi.search.domain.model.Search;
import com.turi.touristicplace.domain.model.TouristicPlaceType;

import java.time.LocalDate;
import java.util.List;

public interface SearchService
{
    Search search(final Long limit,
                  final Long cursor,
                  final String query,
                  final LocalDate dateFrom,
                  final LocalDate dateTo);

    Search searchByStay(final Long limit,
                        final Long cursor,
                        final String query,
                        final LocalDate dateFrom,
                        final LocalDate dateTo,
                        final TouristicPlaceType type);

    Search searchByAttraction(final Long limit,
                              final Long cursor,
                              final String query,
                              final LocalDate dateFrom,
                              final LocalDate dateTo,
                              final AttractionType type);

    List<String> completeForTouristicPlace(final String query);

    List<String> completeForStay(final String query);

    List<String> completeForAttraction(final String query);
}
