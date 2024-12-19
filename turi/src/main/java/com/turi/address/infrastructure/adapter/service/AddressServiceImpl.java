package com.turi.address.infrastructure.adapter.service;

import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressRepository;
import com.turi.address.domain.port.AddressService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService
{
    private static final Logger LOG = LoggerFactory.getLogger(AddressService.class);

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
        try
        {
            repository.deleteById(id);
        }
        catch (final DataIntegrityViolationException ex)
        {
            LOG.info(String.format("The new address has been successfully assigned, but the old one with the ID '%s' has not been removed because it is assigned to an account or tourist place.", id));
        }
    }
}
