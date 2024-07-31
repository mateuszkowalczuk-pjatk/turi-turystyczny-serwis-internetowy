package com.turi.address.infrastructure.adapter.interfaces;

import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressFacade
{
    private final AddressService service;

    public Address getByAddress(final Address address)
    {
        validation(address);

        return service.getByAddress(address);
    }

    public Address createAddress(final Address address)
    {
        validation(address);

        return AddressResponse.of(service.createAddress(address));
    }

    public void validation(final Address address)
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
}
