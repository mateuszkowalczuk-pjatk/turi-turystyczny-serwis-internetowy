package com.turi.offer.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.AttractionType;
import com.turi.infrastructure.common.ContextHandler;
import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.offer.domain.model.Offer;
import com.turi.offer.domain.model.Search;
import com.turi.offer.domain.model.SearchMode;
import com.turi.offer.domain.port.FavouriteService;
import com.turi.offer.domain.port.SearchService;
import com.turi.touristicplace.domain.model.TouristicPlaceType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class OfferFacade
{
    private final SearchService searchService;
    private final FavouriteService favouriteService;

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
            return OfferResponse.of(searchService.searchByStay(searchLimit, searchTouristicPlaceId, searchRank, query, dateFrom, dateTo, touristicPlaceType));
        }

        if (mode.equals(SearchMode.ATTRACTION))
        {
            return OfferResponse.of(searchService.searchByAttraction(searchLimit, searchTouristicPlaceId, searchRank, query, dateFrom, dateTo, attractionType));
        }

        if (query.isEmpty() || query.isBlank())
        {
            throw new BadRequestParameterException("Parameter query must not be empty or blank for 'ALL' search mode.");
        }

        return OfferResponse.of(searchService.search(searchLimit, searchTouristicPlaceId, searchRank, query, dateFrom, dateTo));
    }

    public ResponseEntity<List<String>> autocomplete(final SearchMode mode, final String query)
    {
        if (mode == null || query == null)
        {
            throw new BadRequestParameterException("Parameters mode and query must not be null.");
        }

        if (mode.equals(SearchMode.STAY))
        {
            return OfferResponse.ofSearch(searchService.completeForStay(query));
        }

        if (mode.equals(SearchMode.ATTRACTION))
        {
            return OfferResponse.ofSearch(searchService.completeForAttraction(query));
        }

        return OfferResponse.ofSearch(searchService.completeForTouristicPlace(query));
    }

    public ResponseEntity<List<Offer>> getAllFavouriteOffersByAccount()
    {
        final var accountId = ContextHandler.getIdFromContext();

        return OfferResponse.ofFavourite(favouriteService.getAllByAccount(accountId));
    }

    public ResponseEntity<Boolean> isOfferFavouriteForAccount(final String touristicPlaceId)
    {
        if (touristicPlaceId == null)
        {
            throw new BadRequestParameterException("Parameter touristicPlaceId must not be null");
        }

        final var accountId = ContextHandler.getIdFromContext();

        return OfferResponse.of(favouriteService.isOfferForAccount(accountId, ObjectId.of(touristicPlaceId).getValue()));
    }

    public ResponseEntity<?> addFavouriteOfferForAccount(final String touristicPlaceId)
    {
        if (touristicPlaceId == null)
        {
            throw new BadRequestParameterException("Parameter touristicPlaceId must not be null");
        }

        final var accountId = ContextHandler.getIdFromContext();

        favouriteService.create(accountId, ObjectId.of(touristicPlaceId).getValue());

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteFavouriteOfferForAccount(final String touristicPlaceId)
    {
        if (touristicPlaceId == null)
        {
            throw new BadRequestParameterException("Parameter touristicPlaceId must not be null");
        }

        final var accountId = ContextHandler.getIdFromContext();

        favouriteService.delete(accountId, ObjectId.of(touristicPlaceId).getValue());

        return ResponseEntity.ok().build();
    }
}
