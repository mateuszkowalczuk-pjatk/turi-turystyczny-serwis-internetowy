package com.turi.touristicplace.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.AttractionType;
import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;
import com.turi.touristicplace.domain.model.TouristicPlaceType;
import com.turi.touristicplace.domain.port.TouristicPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class TouristicPlaceFacade
{
    private final TouristicPlaceService service;

    public List<TouristicPlace> getTouristicPlacesForSearch(final String query,
                                                            final LocalDate dateFrom,
                                                            final LocalDate dateTo,
                                                            final Long limit,
                                                            final Long cursor)
    {
        return service.getForSearch(query, dateFrom, dateTo, limit, cursor);
    }

    public List<TouristicPlace> getTouristicPlacesByStaysForSearch(final String query,
                                                                   final LocalDate dateFrom,
                                                                   final LocalDate dateTo,
                                                                   final Long limit,
                                                                   final Long cursor,
                                                                   final TouristicPlaceType type)
    {
        return service.getByStaysForSearch(query, dateFrom, dateTo, type, limit, cursor);
    }

    public List<TouristicPlace> getTouristicPlacesByAttractionsForSearch(final String query,
                                                                         final LocalDate dateFrom,
                                                                         final LocalDate dateTo,
                                                                         final Long limit,
                                                                         final Long cursor,
                                                                         final AttractionType type)
    {
        return service.getByAttractionsForSearch(query, dateFrom, dateTo, type, limit, cursor);
    }

    public List<String> completeTouristicPlacesNames(final String query)
    {
        return service.completeNames(query);
    }

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

    public ResponseEntity<List<GuaranteedService>> getAllTouristicPlaceGuaranteedServicesByTouristicPlaceId(final Long touristicPlaceId)
    {
        return TouristicPlaceResponse.of(service.getAllGuaranteedServicesByTouristicPlaceId(touristicPlaceId));
    }

    public ResponseEntity<?> createTouristicPlace()
    {
        service.create();

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<GuaranteedService> createTouristicPlaceGuaranteedService(final GuaranteedService guaranteedService)
    {
        if (guaranteedService == null)
        {
            throw new BadRequestParameterException("Parameter guaranteedService must not be null!");
        }

        return TouristicPlaceResponse.of(service.createGuaranteedService(guaranteedService));
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