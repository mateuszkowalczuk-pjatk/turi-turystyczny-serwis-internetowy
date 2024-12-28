package com.turi.attraction.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.Attraction;
import com.turi.infrastructure.exception.BadRequestResponseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AttractionResponse
{
    public static ResponseEntity<List<Attraction>> of(final List<Attraction> attractions)
    {
        if (attractions == null)
        {
            throw new BadRequestResponseException("Attractions response must not be null.");
        }

        return ResponseEntity.ok(attractions);
    }

    public static ResponseEntity<Attraction> of(final Attraction attraction)
    {
        if (attraction == null)
        {
            throw new BadRequestResponseException("Attraction response must not be null.");
        }

        return ResponseEntity.ok(attraction);
    }
}
