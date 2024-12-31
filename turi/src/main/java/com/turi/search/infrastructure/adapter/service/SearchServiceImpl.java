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
                         final Long touristicPlaceId,
                         final Double rank,
                         final String query,
                         final LocalDate dateFrom,
                         final LocalDate dateTo)
    {
        final var touristicPlacesForSearch = touristicPlaceFacade.getTouristicPlacesForSearch(query, dateFrom, dateTo, limit, touristicPlaceId, rank);

        final var searchTouristicPlaces = touristicPlacesForSearch.stream()
                .map(result -> (TouristicPlace) result[0])
                .map(touristicPlace -> getSearchTouristicPlace(touristicPlace, dateFrom, dateTo))
                .toList();

        final var searchTouristicPlaceId = getSearchTouristicPlaceId(searchTouristicPlaces);

        final var searchRank = getSearchRank(touristicPlacesForSearch);

        return Search.builder()
                .withSearchTouristicPlaces(searchTouristicPlaces)
                .withTouristicPlaceId(searchTouristicPlaceId)
                .withRank(searchRank)
                .build();
    }

    @Override
    public Search searchByStay(final Long limit,
                               final Long touristicPlaceId,
                               final Double rank,
                               final String query,
                               final LocalDate dateFrom,
                               final LocalDate dateTo,
                               final TouristicPlaceType touristicPlaceType)
    {
        final var touristicPlacesForSearch = touristicPlaceFacade.getTouristicPlacesByStaysForSearch(query, dateFrom, dateTo, limit, touristicPlaceId, rank, touristicPlaceType);

        final var searchTouristicPlaces = touristicPlacesForSearch.stream()
                .map(result -> (TouristicPlace) result[0])
                .filter(touristicPlace -> touristicPlaceType == null || touristicPlace.getTouristicPlaceType().equals(touristicPlaceType))
                .map(touristicPlace -> getSearchTouristicPlace(touristicPlace, dateFrom, dateTo))
                .toList();

        final var searchTouristicPlaceId = getSearchTouristicPlaceId(searchTouristicPlaces);

        final var searchRank = getSearchRank(touristicPlacesForSearch);

        return Search.builder()
                .withSearchTouristicPlaces(searchTouristicPlaces)
                .withTouristicPlaceId(searchTouristicPlaceId)
                .withRank(searchRank)
                .build();
    }

    @Override
    public Search searchByAttraction(final Long limit,
                                     final Long touristicPlaceId,
                                     final Double rank,
                                     final String query,
                                     final LocalDate dateFrom,
                                     final LocalDate dateTo,
                                     final AttractionType attractionType)
    {
        final var touristicPlacesForSearch = touristicPlaceFacade.getTouristicPlacesByAttractionsForSearch(query, dateFrom, dateTo, limit, touristicPlaceId, rank, attractionType);

        final var searchTouristicPlaces = touristicPlacesForSearch.stream()
                .map(result -> (TouristicPlace) result[0])
                .map(touristicPlace -> {
                    final var guaranteedServices = getTouristicPlaceGuaranteedServices(touristicPlace.getTouristicPlaceId());

                    final var stays = getTouristicPlaceStays(touristicPlace.getTouristicPlaceId(), dateFrom, dateTo);

                    final var attractions = getTouristicPlaceAttractions(touristicPlace.getTouristicPlaceId(), dateFrom, dateTo)
                            .stream()
                            .filter(attraction -> attractionType == null || attraction.getAttractionType().equals(attractionType))
                            .toList();

                    return Search.SearchTouristicPlace.builder()
                            .withTouristicPlace(touristicPlace)
                            .withGuaranteedServices(guaranteedServices)
                            .withStays(stays)
                            .withAttractions(attractions)
                            .build();
                })
                .toList();

        final var searchTouristicPlaceId = getSearchTouristicPlaceId(searchTouristicPlaces);

        final var searchRank = getSearchRank(touristicPlacesForSearch);

        return Search.builder()
                .withSearchTouristicPlaces(searchTouristicPlaces)
                .withTouristicPlaceId(searchTouristicPlaceId)
                .withRank(searchRank)
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

    private Long getSearchTouristicPlaceId(final List<Search.SearchTouristicPlace> searchTouristicPlaces)
    {
        return searchTouristicPlaces.isEmpty() ? null : searchTouristicPlaces.get(searchTouristicPlaces.size() - 1).getTouristicPlace().getTouristicPlaceId();
    }

    private Double getSearchRank(final List<Object[]> touristicPlacesForSearch)
    {
        final var ranks = touristicPlacesForSearch.stream()
                .map(result -> Double.valueOf(String.valueOf(result[1])))
                .toList();

        return ranks.isEmpty() ? null : ranks.get(ranks.size() - 1);
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
