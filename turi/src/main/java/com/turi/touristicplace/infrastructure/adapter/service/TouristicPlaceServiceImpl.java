package com.turi.touristicplace.infrastructure.adapter.service;

import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.attraction.domain.model.AttractionType;
import com.turi.premium.infrastructure.adapter.interfaces.PremiumFacade;
import com.turi.touristicplace.domain.exception.TouristicPlaceAlreadyExistsException;
import com.turi.touristicplace.domain.exception.TouristicPlaceUniqueAddressException;
import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;
import com.turi.touristicplace.domain.model.TouristicPlaceType;
import com.turi.touristicplace.domain.port.GuaranteedServiceService;
import com.turi.touristicplace.domain.port.TouristicPlaceRepository;
import com.turi.touristicplace.domain.port.TouristicPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class TouristicPlaceServiceImpl implements TouristicPlaceService
{
    private final AddressFacade addressFacade;
    private final PremiumFacade premiumFacade;
    private final TouristicPlaceRepository repository;
    private final GuaranteedServiceService guaranteedServiceService;

    @Override
    public List<Object[]> getForSearch(final String query,
                                       final LocalDate dateFrom,
                                       final LocalDate dateTo,
                                       final Long limit,
                                       final Long touristicPlaceId,
                                       final Double rank)
    {
        return repository.findForSearch(query, dateFrom, dateTo, limit, touristicPlaceId, rank);
    }

    @Override
    public List<Object[]> getByStaysForSearch(final String query,
                                              final LocalDate dateFrom,
                                              final LocalDate dateTo,
                                              final TouristicPlaceType touristicPlaceType,
                                              final Long limit,
                                              final Long touristicPlaceId,
                                              final Double rank)
    {
        return repository.findByStaysForSearch(query, dateFrom, dateTo, touristicPlaceType != null ? touristicPlaceType.getValue() : null, limit, touristicPlaceId, rank);
    }

    @Override
    public List<Object[]> getByAttractionsForSearch(final String query,
                                                    final LocalDate dateFrom,
                                                    final LocalDate dateTo,
                                                    final AttractionType attractionType,
                                                    final Long limit,
                                                    final Long touristicPlaceId,
                                                    final Double rank)
    {
        return repository.findByAttractionsForSearch(query, dateFrom, dateTo, attractionType != null ? attractionType.getValue() : null, limit, touristicPlaceId, rank);
    }

    @Override
    public List<String> completeNames(final String query)
    {
        return repository.findForAutocomplete(query);
    }

    @Override
    public TouristicPlace getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public TouristicPlace getByPremiumId()
    {
        final var premiumId = Objects.requireNonNull(premiumFacade.getPremiumByAccount().getBody()).getPremiumId();

        return repository.findByPremiumId(premiumId);
    }

    @Override
    public Boolean isAddressExists(final String country,
                                   final String city,
                                   final String zipCode,
                                   final String street,
                                   final String buildingNumber,
                                   final Integer apartmentNumber)
    {
        final var address = addressFacade.getAddressByAddress(country, city, zipCode, street, buildingNumber, apartmentNumber);

        final var addressId = getByPremiumId().getAddressId();

        if (address == null || (addressId != null && addressId.equals(address.getAddressId())))
        {
            return false;
        }

        return repository.findByAddressId(address.getAddressId()) != null;
    }

    @Override
    public List<GuaranteedService> getAllGuaranteedServices()
    {
        return guaranteedServiceService.getAllByTouristicPlaceId(getByPremiumId().getTouristicPlaceId());
    }

    @Override
    public List<GuaranteedService> getAllGuaranteedServicesByTouristicPlaceId(final Long touristicPlaceId)
    {
        return guaranteedServiceService.getAllByTouristicPlaceId(touristicPlaceId);
    }

    @Override
    public void create()
    {
        if (getByPremiumId() != null)
        {
            throw new TouristicPlaceAlreadyExistsException();
        }

        final var premiumId = Objects.requireNonNull(premiumFacade.getPremiumByAccount().getBody()).getPremiumId();

        final var touristicPlace = TouristicPlace.builder()
                .withPremiumId(premiumId)
                .build();

        repository.insert(touristicPlace);
    }

    @Override
    public GuaranteedService createGuaranteedService(final GuaranteedService guaranteedService)
    {
        return guaranteedServiceService.create(guaranteedService);
    }

    @Override
    public void updateDetails(final Long id, final TouristicPlace touristicPlace)
    {
        final var currentTouristicPlace = getById(id);

        if (touristicPlace.getAddressId() != null)
        {
            try
            {
                final var address = addressFacade.getAddressById(String.valueOf(touristicPlace.getAddressId())).getBody();

                if (address != null && isAddressExists(address.getCountry(), address.getCity(), address.getZipCode(), address.getStreet(), address.getBuildingNumber(), address.getApartmentNumber()))
                {
                    throw new TouristicPlaceUniqueAddressException(touristicPlace.getAddressId());
                }
            }
            catch (AddressNotFoundException ex)
            {
                throw new TouristicPlaceUniqueAddressException(touristicPlace.getAddressId());
            }
        }

        final var touristicPlaceToUpdate = TouristicPlace.builder()
                .withPremiumId(currentTouristicPlace.getPremiumId())
                .withAddressId(touristicPlace.getAddressId())
                .withTouristicPlaceType(touristicPlace.getTouristicPlaceType())
                .withName(touristicPlace.getName())
                .withDescription(touristicPlace.getDescription())
                .withInformation(touristicPlace.getInformation())
                .withOwnerDescription(touristicPlace.getOwnerDescription())
                .withCheckInTimeFrom(touristicPlace.getCheckInTimeFrom())
                .withCheckInTimeTo(touristicPlace.getCheckInTimeTo())
                .withCheckOutTimeFrom(touristicPlace.getCheckOutTimeFrom())
                .withCheckOutTimeTo(touristicPlace.getCheckOutTimeTo())
                .withPrepayment(touristicPlace.isPrepayment())
                .withCancelReservationDays(touristicPlace.getCancelReservationDays())
                .build();

        if ((touristicPlace.getAddressId() != null && currentTouristicPlace.getAddressId() != null && !touristicPlace.getAddressId().equals(currentTouristicPlace.getAddressId()))
                || (touristicPlace.getAddressId() == null && currentTouristicPlace.getAddressId() != null))
        {
            addressFacade.deleteAddressById(currentTouristicPlace.getAddressId());
        }

        repository.update(id, touristicPlaceToUpdate);
    }

    @Override
    public void deleteGuaranteedService(final Long guaranteedServiceId)
    {
        guaranteedServiceService.deleteById(guaranteedServiceId);
    }
}
