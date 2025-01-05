package com.turi.offer.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.AttractionType;
import com.turi.offer.domain.model.Offer;
import com.turi.offer.domain.model.Search;
import com.turi.offer.domain.model.SearchMode;
import com.turi.touristicplace.domain.model.TouristicPlaceType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/offer", produces = "application/json")
public class OfferRestController
{
    private final OfferFacade facade;

    @GetMapping("/search")
    public ResponseEntity<Search> search(@RequestParam final SearchMode mode,
                                         @RequestParam final String limit,
                                         @RequestParam(required = false) final String touristicPlaceId,
                                         @RequestParam(required = false) final String rank,
                                         @RequestParam(required = false) final String query,
                                         @RequestParam(required = false) final LocalDate dateFrom,
                                         @RequestParam(required = false) final LocalDate dateTo,
                                         @RequestParam(required = false) final TouristicPlaceType touristicPlaceType,
                                         @RequestParam(required = false) final AttractionType attractionType)
    {
        return facade.search(mode, limit, touristicPlaceId, rank, query, dateFrom, dateTo, touristicPlaceType, attractionType);
    }

    @GetMapping("/autocomplete")
    public ResponseEntity<List<String>> autocomplete(@RequestParam final SearchMode mode,
                                                     @RequestParam final String query)
    {
        return facade.autocomplete(mode, query);
    }

    @GetMapping("/getAllFavouriteByAccount")
    public ResponseEntity<List<Offer>> getAllFavouriteOffersByAccount()
    {
        return facade.getAllFavouriteOffersByAccount();
    }

    @GetMapping("/isFavouriteForAccount")
    public ResponseEntity<Boolean> isOfferFavouriteForAccount(@RequestParam final String touristicPlaceId)
    {
        return facade.isOfferFavouriteForAccount(touristicPlaceId);
    }

    @PostMapping("/addFavouriteForAccount")
    public ResponseEntity<?> addFavouriteOfferForAccount(@RequestParam final String touristicPlaceId)
    {
        return facade.addFavouriteOfferForAccount(touristicPlaceId);
    }

    @DeleteMapping("/deleteFavouriteForAccount")
    public ResponseEntity<?> deleteFavouriteOfferForAccount(@RequestParam final String touristicPlaceId)
    {
        return facade.deleteFavouriteOfferForAccount(touristicPlaceId);
    }
}
