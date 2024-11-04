package com.turi.address.infrastructure.adapter.service;

import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressRepository;
import com.turi.address.domain.port.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService
{
    private final AddressRepository repository;

    @Override
    public Address getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public Address getByAddress(final Address address)
    {
        return repository.findByAddress(address);
    }

    @Override
    public Address create(final Address address)
    {
        final var existingAddress = getByAddress(address);

        if (existingAddress != null)
        {
            return existingAddress;
        }

        final var id = repository.insert(address);

        return getById(id);
    }

    @Override
    public void deleteById(final Long id)
    {
        repository.delete(id);
    }
}
