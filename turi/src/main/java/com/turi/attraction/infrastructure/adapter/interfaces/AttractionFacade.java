package com.turi.attraction.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.Attraction;
import com.turi.attraction.domain.port.AttractionService;
import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AttractionFacade
{
    private final AttractionService service;

    public ResponseEntity<List<Attraction>> getAllAttractionsByTouristicPlaceId(final String touristicPlaceId)
    {
        if (touristicPlaceId == null)
        {
            throw new BadRequestParameterException("Parameter touristicPlaceId must not be null.");
        }

        final var id = ObjectId.of(touristicPlaceId).getValue();

        return AttractionResponse.of(service.getAllByTouristicPlaceId(id));
    }

    public ResponseEntity<Attraction> getAttractionById(final String id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter ID must not be null.");
        }

        final var attractionId = ObjectId.of(id).getValue();

        return AttractionResponse.of(service.getById(attractionId));
    }

    public List<String> completeAttractionsNames(final String query)
    {
        return service.completeNames(query);
    }

    public ResponseEntity<Attraction> createAttraction(final Attraction attraction)
    {
        if (attraction == null)
        {
            throw new BadRequestParameterException("Parameter attraction must not be null.");
        }

        return AttractionResponse.of(service.create(attraction));
    }

    public ResponseEntity<Attraction> updateAttraction(final String id, final Attraction attraction)
    {
        if (id == null || attraction == null)
        {
            throw new BadRequestParameterException("Parameters ID and attraction must not be null.");
        }

        final var attractionId = ObjectId.of(id).getValue();

        service.update(attractionId, attraction);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteAttraction(final String id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter ID must not be null.");
        }

        final var attractionId = ObjectId.of(id).getValue();

        service.delete(attractionId);

        return ResponseEntity.ok().build();
    }
}
