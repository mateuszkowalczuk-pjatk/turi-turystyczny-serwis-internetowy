package com.turi.attraction.infrastructure.adapter.service;

import com.turi.attraction.domain.model.Attraction;
import com.turi.attraction.domain.model.PriceType;
import com.turi.attraction.domain.port.AttractionRepository;
import com.turi.attraction.domain.port.AttractionService;
import com.turi.image.infrastructure.adapter.interfaces.ImageFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AttractionServiceImpl implements AttractionService
{
    private final ImageFacade imageFacade;
    private final AttractionRepository repository;

    @Override
    public List<String> completeNames(final String query)
    {
        return repository.findForAutocomplete(query);
    }

    @Override
    public List<Attraction> getAllByTouristicPlaceId(final Long touristicPlaceId)
    {
        return repository.findAllByTouristicPlaceId(touristicPlaceId);
    }

    @Override
    public Attraction getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public Attraction create(final Attraction attraction)
    {
        validatePriceType(attraction);

        return getById(repository.insert(attraction));
    }

    @Override
    public void update(final Long id, final Attraction attraction)
    {
        final var currentAttraction = getById(id);

        validatePriceType(attraction);

        final var attractionToUpdate = Attraction.builder()
                .withTouristicPlaceId(currentAttraction.getTouristicPlaceId())
                .withAttractionType(attraction.getAttractionType())
                .withName(attraction.getName())
                .withDescription(attraction.getDescription())
                .withPrice(attraction.getPrice())
                .withPriceType(attraction.getPriceType())
                .withPrepayment(attraction.getPrepayment())
                .withCancelReservationDays(attraction.getCancelReservationDays())
                .withMaxPeopleNumber(attraction.getMaxPeopleNumber())
                .withMaxItems(attraction.getMaxItems())
                .withDateFrom(attraction.getDateFrom())
                .withDateTo(attraction.getDateTo())
                .withHourFrom(attraction.getHourFrom())
                .withHourTo(attraction.getHourTo())
                .withDaysReservationBefore(attraction.getDaysReservationBefore())
                .build();

        repository.update(id, attractionToUpdate);
    }

    private void validatePriceType(final Attraction attraction)
    {
        if ((attraction.getPriceType().equals(PriceType.PERSON) && attraction.getMaxPeopleNumber() == null)
                || (attraction.getPriceType().equals(PriceType.ITEM) && attraction.getMaxItems() == null))
        {
            throw new BadRequestParameterException("Specified attraction is invalid. Attraction with price type person must have not null field maxPeopleNumber and with price type item must have not null field maxItems.");
        }
    }

    @Override
    public void delete(final Long id)
    {
        imageFacade.deleteAllImagesByAttractionId(id);

        repository.delete(id);
    }
}
