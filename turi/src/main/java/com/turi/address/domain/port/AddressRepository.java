package com.turi.address.domain.port;

import com.turi.address.domain.model.Address;

public interface AddressRepository
{
    Address findById(final Long addressId);

    Address findByAddress(final Address address);

    Long insert(final Address address);

    void delete(final Long addressId);
}
