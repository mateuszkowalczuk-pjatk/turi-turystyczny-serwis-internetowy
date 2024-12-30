package com.turi.search.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.AttractionType;
import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.search.domain.model.Search;
import com.turi.search.domain.model.SearchMode;
import com.turi.search.domain.port.SearchService;
import com.turi.touristicplace.domain.model.TouristicPlaceType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class SearchFacade
{
    private final SearchService service;

    public ResponseEntity<Search> search(final SearchMode mode,
                                         final String limit,
                                         final String touristicPlaceId,
                                         final String rank,
                                         final String query,
                                         final LocalDate dateFrom,
                                         final LocalDate dateTo,
                                         final TouristicPlaceType touristicPlaceType,
                                         final AttractionType attractionType)
    {
        if (mode == null || limit == null)
        {
            throw new BadRequestParameterException("Parameters mode and limit must not be null.");
        }

        if ((dateFrom == null && dateTo != null) || (dateFrom != null && dateTo == null))
        {
            throw new BadRequestParameterException("Parameters dateFrom and dateTo must not be null or both must have date value.");
        }

        final var searchLimit = ObjectId.of(limit).getValue();

        final var searchTouristicPlaceId = touristicPlaceId != null ? ObjectId.of(touristicPlaceId).getValue() : null;

        final var searchRank = rank != null ? Double.valueOf(rank) : null;

        if ((searchTouristicPlaceId == null && searchRank != null) || (searchTouristicPlaceId != null && searchRank == null))
        {
            throw new BadRequestParameterException("Parameters touristicPlaceId and rank must not be null or both must have cursor value.");
        }

        if (mode.equals(SearchMode.STAY))
        {
            return SearchResponse.of(service.searchByStay(searchLimit, searchTouristicPlaceId, searchRank, query, dateFrom, dateTo, touristicPlaceType));
        }

        if (mode.equals(SearchMode.ATTRACTION))
        {
            return SearchResponse.of(service.searchByAttraction(searchLimit, searchTouristicPlaceId, searchRank, query, dateFrom, dateTo, attractionType));
        }

        if (query.isEmpty() || query.isBlank())
        {
            throw new BadRequestParameterException("Parameter query must not be empty or blank for 'ALL' search mode.");
        }

        return SearchResponse.of(service.search(searchLimit, searchTouristicPlaceId, searchRank, query, dateFrom, dateTo));
    }

    public ResponseEntity<List<String>> autocomplete(final SearchMode mode, final String query)
    {
        if (mode == null || query == null)
        {
            throw new BadRequestParameterException("Parameters mode and query must not be null.");
        }

        if (mode.equals(SearchMode.STAY))
        {
            return SearchResponse.of(service.completeForStay(query));
        }

        if (mode.equals(SearchMode.ATTRACTION))
        {
            return SearchResponse.of(service.completeForAttraction(query));
        }

        return SearchResponse.of(service.completeForTouristicPlace(query));
    }
}
