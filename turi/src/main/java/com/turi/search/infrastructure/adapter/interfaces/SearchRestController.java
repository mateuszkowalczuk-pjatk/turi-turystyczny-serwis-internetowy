package com.turi.search.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.AttractionType;
import com.turi.search.domain.model.Search;
import com.turi.search.domain.model.SearchMode;
import com.turi.touristicplace.domain.model.TouristicPlaceType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/search", produces = "application/json")
public class SearchRestController
{
    private final SearchFacade facade;

    @GetMapping("/")
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
}
