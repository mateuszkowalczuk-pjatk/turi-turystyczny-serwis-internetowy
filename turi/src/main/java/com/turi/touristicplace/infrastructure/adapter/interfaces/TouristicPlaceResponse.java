package com.turi.touristicplace.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TouristicPlaceResponse
{
    public static ResponseEntity<TouristicPlace> of(final TouristicPlace touristicPlace)
    {
        if (touristicPlace == null)
        {
            throw new BadRequestResponseException("Touristic place response must not be null.");
        }

        return ResponseEntity.ok(touristicPlace);
    }

    public static ResponseEntity<Boolean> of(final Boolean result)
    {
        if (result == null)
        {
            throw new BadRequestResponseException("Touristic place check result response must not be null.");
        }

        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<List<GuaranteedService>> of(final List<GuaranteedService> guaranteedServices)
    {
        if (guaranteedServices == null)
        {
            throw new BadRequestResponseException("Touristic place guaranteed services response must not be null.");
        }

        return ResponseEntity.ok(guaranteedServices);
    }
}
