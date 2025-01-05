package com.turi.offer.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.offer.domain.model.Offer;
import com.turi.offer.domain.model.Search;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OfferResponse
{
    public static ResponseEntity<Search> of(final Search search)
    {
        if (search == null)
        {
            throw new BadRequestResponseException("Offer search response must not be null");
        }

        return ResponseEntity.ok(search);
    }
    public static ResponseEntity<List<String>> ofSearch(final List<String> autocomplete)
    {
        if (autocomplete == null)
        {
            throw new BadRequestResponseException("Offer autocomplete response must not be null");
        }

        return ResponseEntity.ok(autocomplete);
    }

    public static ResponseEntity<List<Offer>> ofFavourite(final List<Offer> offers)
    {
        if (offers == null)
        {
            throw new BadRequestResponseException("Offer favourite offers response must not be null");
        }

        return ResponseEntity.ok(offers);
    }

    public static ResponseEntity<Boolean> of(final Boolean condition)
    {
        if (condition == null)
        {
            throw new BadRequestResponseException("Offer favourite condition response must not be null");
        }

        return ResponseEntity.ok(condition);
    }
}
