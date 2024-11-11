package com.turi.address.domain.port;

import com.turi.address.domain.model.Address;

public interface AddressService
{
    Address getById(final Long id);

    Address getByAddress(final Address address);

    Address create(final Address address);

    void deleteById(final Long id);
}
