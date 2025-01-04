package com.turi.offer.infrastructure.adapter.service;

import com.turi.attraction.infrastructure.adapter.interfaces.AttractionFacade;
import com.turi.offer.domain.exception.UniqueFavouriteException;
import com.turi.offer.domain.model.Offer;
import com.turi.offer.domain.port.FavouriteRepository;
import com.turi.offer.domain.port.FavouriteService;
import com.turi.stay.infrastructure.adapter.interfaces.StayFacade;
import com.turi.touristicplace.infrastructure.adapter.interfaces.TouristicPlaceFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavouriteServiceImpl implements FavouriteService
{
    private final StayFacade stayFacade;
    private final FavouriteRepository repository;
    private final AttractionFacade attractionFacade;
    private final TouristicPlaceFacade touristicPlaceFacade;

    @Override
    public List<Offer> getAllByAccount(final Long accountId)
    {
        final var touristicPlaces = repository.findAllByAccount(accountId).stream()
                .map(favourite -> touristicPlaceFacade.getTouristicPlaceById(favourite.getTouristicPlaceId()))
                .toList();

        return touristicPlaces.stream()
                .map(touristicPlace -> {
                    final var guaranteedServices = touristicPlaceFacade.getAllTouristicPlaceGuaranteedServicesByTouristicPlaceId(touristicPlace.getTouristicPlaceId()).getBody();

                    final var stays = stayFacade.getAllStaysByTouristicPlaceId(String.valueOf(touristicPlace.getTouristicPlaceId())).getBody();

                    final var attractions = attractionFacade.getAllAttractionsByTouristicPlaceId(String.valueOf(touristicPlace.getTouristicPlaceId())).getBody();

                    return Offer.builder()
                            .withTouristicPlace(touristicPlace)
                            .withGuaranteedServices(guaranteedServices)
                            .withStays(stays)
                            .withAttractions(attractions)
                            .build();
                })
                .toList();
    }

    @Override
    public Boolean isOfferForAccount(final Long accountId, final Long touristicPlaceId)
    {
        return repository.findByAccountIdAndTouristicPlaceId(accountId, touristicPlaceId) != null;
    }

    @Override
    public void addForAccount(final Long accountId, final Long touristicPlaceId)
    {
        if (isOfferForAccount(accountId, touristicPlaceId))
        {
            throw new UniqueFavouriteException();
        }

        repository.insertForAccount(accountId, touristicPlaceId);
    }

    @Override
    public void deleteForAccount(final Long accountId, final Long touristicPlaceId)
    {
        if (isOfferForAccount(accountId, touristicPlaceId))
        {
            repository.deleteForAccount(accountId, touristicPlaceId);
        }
    }
}
