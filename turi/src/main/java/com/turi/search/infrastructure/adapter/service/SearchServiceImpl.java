package com.turi.search.infrastructure.adapter.service;

import com.turi.attraction.domain.model.Attraction;
import com.turi.attraction.domain.model.AttractionType;
import com.turi.attraction.infrastructure.adapter.interfaces.AttractionFacade;
import com.turi.search.domain.model.Search;
import com.turi.search.domain.port.SearchService;
import com.turi.stay.domain.model.StayDto;
import com.turi.stay.infrastructure.adapter.interfaces.StayFacade;
import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;
import com.turi.touristicplace.domain.model.TouristicPlaceType;
import com.turi.touristicplace.infrastructure.adapter.interfaces.TouristicPlaceFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService
{
    private final StayFacade stayFacade;
    private final AttractionFacade attractionFacade;
    private final TouristicPlaceFacade touristicPlaceFacade;

    @Override
    public Search search(final Long limit,
                         final Long cursor,
                         final String query,
                         final LocalDate dateFrom,
                         final LocalDate dateTo)
    {
        final var searchTouristicPlaces = touristicPlaceFacade.getTouristicPlacesForSearch(query, dateFrom, dateTo, limit, cursor).stream()
                .map(touristicPlace -> getSearchTouristicPlace(touristicPlace, dateFrom, dateTo))
                .toList();

        final var searchCursor = getSearchCursor(searchTouristicPlaces);

        return Search.builder()
                .withSearchTouristicPlaces(searchTouristicPlaces)
                .withCursor(searchCursor)
                .build();
    }

    @Override
    public Search searchByStay(final Long limit,
                               final Long cursor,
                               final String query,
                               final LocalDate dateFrom,
                               final LocalDate dateTo,
                               final TouristicPlaceType type)
    {
        final var searchTouristicPlaces = touristicPlaceFacade.getTouristicPlacesByStaysForSearch(query, dateFrom, dateTo, limit, cursor, type).stream()
                .filter(touristicPlace -> touristicPlace.getTouristicPlaceType().equals(type))
                .map(touristicPlace -> getSearchTouristicPlace(touristicPlace, dateFrom, dateTo))
                .toList();

        final var searchCursor = getSearchCursor(searchTouristicPlaces);

        return Search.builder()
                .withSearchTouristicPlaces(searchTouristicPlaces)
                .withCursor(searchCursor)
                .build();
    }

    @Override
    public Search searchByAttraction(final Long limit,
                                     final Long cursor,
                                     final String query,
                                     final LocalDate dateFrom,
                                     final LocalDate dateTo,
                                     final AttractionType type)
    {
        final var searchTouristicPlaces = touristicPlaceFacade.getTouristicPlacesByAttractionsForSearch(query, dateFrom, dateTo, limit, cursor, type).stream()
                .map(touristicPlace -> {
                    final var guaranteedServices = getTouristicPlaceGuaranteedServices(touristicPlace.getTouristicPlaceId());

                    final var stays = getTouristicPlaceStays(touristicPlace.getTouristicPlaceId(), dateFrom, dateTo);

                    final var attractions = getTouristicPlaceAttractions(touristicPlace.getTouristicPlaceId(), dateFrom, dateTo)
                            .stream()
                            .filter(attraction -> attraction.getAttractionType().equals(type))
                            .toList();

                    return Search.SearchTouristicPlace.builder()
                            .withTouristicPlace(touristicPlace)
                            .withGuaranteedServices(guaranteedServices)
                            .withStays(stays)
                            .withAttractions(attractions)
                            .build();
                })
                .toList();

        final var searchCursor = getSearchCursor(searchTouristicPlaces);

        return Search.builder()
                .withSearchTouristicPlaces(searchTouristicPlaces)
                .withCursor(searchCursor)
                .build();
    }

    private Search.SearchTouristicPlace getSearchTouristicPlace(final TouristicPlace touristicPlace,
                                                                final LocalDate dateFrom,
                                                                final LocalDate dateTo)
    {
        final var guaranteedServices = getTouristicPlaceGuaranteedServices(touristicPlace.getTouristicPlaceId());

        final var stays = getTouristicPlaceStays(touristicPlace.getTouristicPlaceId(), dateFrom, dateTo);

        final var attractions = getTouristicPlaceAttractions(touristicPlace.getTouristicPlaceId(), dateFrom, dateTo);

        return Search.SearchTouristicPlace.builder()
                .withTouristicPlace(touristicPlace)
                .withGuaranteedServices(guaranteedServices)
                .withStays(stays)
                .withAttractions(attractions)
                .build();
    }

    private List<GuaranteedService> getTouristicPlaceGuaranteedServices(final Long touristicPlaceId)
    {
        return touristicPlaceFacade.getAllTouristicPlaceGuaranteedServicesByTouristicPlaceId(touristicPlaceId).getBody();
    }

    private List<StayDto> getTouristicPlaceStays(final Long touristicPlaceId,
                                                 final LocalDate dateFrom,
                                                 final LocalDate dateTo)
    {
        return Objects.requireNonNull(stayFacade.getAllStaysByTouristicPlaceId(String.valueOf(touristicPlaceId)).getBody())
                .stream()
                .filter(stay -> validateDateRange(dateFrom, dateTo, stay.getDateFrom(), stay.getDateTo()))
                .toList();
    }

    private List<Attraction> getTouristicPlaceAttractions(final Long touristicPlaceId,
                                                          final LocalDate dateFrom,
                                                          final LocalDate dateTo)
    {
        return Objects.requireNonNull(attractionFacade.getAllAttractionsByTouristicPlaceId(String.valueOf(touristicPlaceId)).getBody())
                .stream()
                .filter(attraction -> validateDateRange(dateFrom, dateTo, attraction.getDateFrom(), attraction.getDateTo()))
                .toList();
    }

    private boolean validateDateRange(final LocalDate searchDateFrom,
                                      final LocalDate searchDateTo,
                                      final LocalDate dateFrom,
                                      final LocalDate dateTo)
    {
        if (searchDateFrom == null && searchDateTo == null)
        {
            return true;
        }

        return (dateFrom == null || searchDateTo == null || !dateFrom.isAfter(searchDateTo))
                && (dateTo == null || searchDateFrom == null || !dateTo.isBefore(searchDateFrom));
    }

    private Long getSearchCursor(final List<Search.SearchTouristicPlace> searchTouristicPlaces)
    {
        return searchTouristicPlaces.isEmpty() ? null : searchTouristicPlaces.get(searchTouristicPlaces.size() - 1).getTouristicPlace().getTouristicPlaceId();
    }

    @Override
    public List<String> completeForTouristicPlace(final String query)
    {
        return touristicPlaceFacade.completeTouristicPlacesNames(query);
    }

    @Override
    public List<String> completeForStay(final String query)
    {
        return stayFacade.completeStaysNames(query);
    }

    @Override
    public List<String> completeForAttraction(final String query)
    {
        return attractionFacade.completeAttractionsNames(query);
    }
}
