package com.turi.touristicplace.infrastructure.adapter.service;

import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.premium.infrastructure.adapter.interfaces.PremiumFacade;
import com.turi.touristicplace.domain.exception.TouristicPlaceUniqueAddressException;
import com.turi.touristicplace.domain.model.GuaranteedService;
import com.turi.touristicplace.domain.model.TouristicPlace;
import com.turi.touristicplace.domain.port.GuaranteedServiceService;
import com.turi.touristicplace.domain.port.TouristicPlaceRepository;
import com.turi.touristicplace.domain.port.TouristicPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

        return repository.findByAddressId(addressId) != null;
    }

    @Override
    public List<GuaranteedService> getAllGuaranteedServices()
    {
        return guaranteedServiceService.getAllByTouristicPlaceId(getByPremiumId().getTouristicPlaceId());
    }

    @Override
    public void create()
    {
        final var premiumId = Objects.requireNonNull(premiumFacade.getPremiumByAccount().getBody()).getPremiumId();

        final var touristicPlace = TouristicPlace.builder()
                .withPremiumId(premiumId)
                .build();

        repository.insert(touristicPlace);
    }

    @Override
    public void createGuaranteedService(final GuaranteedService guaranteedService)
    {
        guaranteedServiceService.create(guaranteedService);
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
