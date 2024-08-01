package com.turi.address.infrastructure.adapter.interfaces;

import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressService;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressFacade
{
    private final AddressService service;

    public Address getByAddress(final String country,
                                final String city,
                                final String zipCode,
                                final String street,
                                final String buildingNumber,
                                final int apartmentNumber)
    {
        final var address = Address.builder()
                .withCountry(country)
                .withCity(city)
                .withZipCode(zipCode)
                .withStreet(street)
                .withBuildingNumber(buildingNumber)
                .withApartmentNumber(apartmentNumber)
                .build();

        validation(address);

        return service.getByAddress(address);
    }

    public Address createAddress(final Address address)
    {
        validation(address);

        zipCodeValidation(address.getZipCode());

        return AddressResponse.of(service.createAddress(address));
    }

    private void validation(final Address address)
    {
        if (address.getCountry() == null
            || address.getCity() == null
            || address.getZipCode() == null
            || address.getStreet() == null
            || address.getBuildingNumber() == null)
        {
            throw new InvalidAddressException();
        }
    }

    private void zipCodeValidation(final String zipCode)
    {
        if (!zipCode.matches("^\\d{2}-\\d{3}$"))
        {
            throw new BadRequestParameterException("Zip code is invalid. Zip code must be in format xx-xxx (x is a digit).");
        }
    }
}
