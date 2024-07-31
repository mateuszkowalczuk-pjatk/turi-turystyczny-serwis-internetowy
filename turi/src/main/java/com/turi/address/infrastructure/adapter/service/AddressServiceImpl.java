package com.turi.address.infrastructure.adapter.service;

import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressRepository;
import com.turi.address.domain.port.AddressService;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService
{
    private final AddressRepository addressRepository;

    @Override
    public Address getById(final Long id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Address ID must not be null!");
        }

        return addressRepository.findById(id);
    }

    @Override
    public Address getByAddress(final Address address)
    {
        return addressRepository.findByAddress(address);
    }

    @Override
    public Address createAddress(final Address address)
    {
        final var existingAddress = addressRepository.findByAddress(address);

        if (existingAddress != null)
        {
            return existingAddress;
        }

        final var addressId = addressRepository.insert(address);

        return getById(addressId);
    }
}
