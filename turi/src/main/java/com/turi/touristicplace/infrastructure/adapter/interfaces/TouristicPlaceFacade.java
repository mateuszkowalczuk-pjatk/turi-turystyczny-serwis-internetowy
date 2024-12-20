package com.turi.touristicplace.infrastructure.adapter.interfaces;

import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;
import com.turi.touristicplace.domain.port.TouristicPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TouristicPlaceFacade
{
    private final TouristicPlaceService service;

    public ResponseEntity<TouristicPlace> getByTouristicPlacePremiumId()
    {
        return TouristicPlaceResponse.of(service.getByPremiumId());
    }

    public ResponseEntity<Boolean> isTouristicPlaceAddressExists(final String country,
                                                                 final String city,
                                                                 final String zipCode,
                                                                 final String street,
                                                                 final String buildingNumber,
                                                                 final String apartmentNumber)
    {
        if (country == null || city == null || zipCode == null || street == null || buildingNumber == null)
        {
            throw new BadRequestParameterException("Parameters country, city, zipCode, street and buildingNumber must not be null.");
        }

        return TouristicPlaceResponse.of(service.isAddressExists(country,
                city,
                zipCode,
                street,
                buildingNumber,
                apartmentNumber != null ? Integer.valueOf(apartmentNumber) : null));
    }

    public ResponseEntity<List<GuaranteedService>> getAllTouristicPlaceGuaranteedServices()
    {
        return TouristicPlaceResponse.of(service.getAllGuaranteedServices());
    }

    public void createTouristicPlace(final Long premiumId)
    {
        if (premiumId == null)
        {
            throw new BadRequestParameterException("Parameter premiumId must not be null!");
        }

        service.create(premiumId);
    }

    public ResponseEntity<?> createTouristicPlaceGuaranteedService(final GuaranteedService guaranteedService)
    {
        if (guaranteedService == null)
        {
            throw new BadRequestParameterException("Parameter guaranteedService must not be null!");
        }

        service.createGuaranteedService(guaranteedService);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> updateTouristicPlaceDetails(final String id, final TouristicPlace touristicPlace)
    {
        if (id == null || touristicPlace == null)
        {
            throw new BadRequestParameterException("Parameters id and touristic place must not be null!");
        }

        final var touristicPlaceId = ObjectId.of(id).getValue();

        service.updateDetails(touristicPlaceId, touristicPlace);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteTouristicPlaceGuaranteedService(final String guaranteedServiceId)
    {
        if (guaranteedServiceId == null)
        {
            throw new BadRequestParameterException("Parameter guaranteedServiceId must not be null!");
        }

        final var id = ObjectId.of(guaranteedServiceId).getValue();

        service.deleteGuaranteedService(id);

        return ResponseEntity.ok().build();
    }
}
