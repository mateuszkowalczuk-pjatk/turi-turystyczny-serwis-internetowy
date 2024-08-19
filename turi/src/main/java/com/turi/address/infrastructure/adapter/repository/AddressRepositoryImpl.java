package com.turi.address.infrastructure.adapter.repository;

import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AddressRepositoryImpl implements AddressRepository
{
    private final AddressRepositoryDao addressRepositoryDao;

    @Override
    public Address findById(final Long addressId)
    {
        return addressRepositoryDao.findById(addressId)
                .map(Address::of)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
    }

    @Override
    public Address findByAddress(final Address address)
    {
        return addressRepositoryDao
                .findByCountryAndCityAndZipCodeAndStreetAndBuildingNumberAndApartmentNumber(
                        address.getCountry(),
                        address.getCity(),
                        address.getZipCode(),
                        address.getStreet(),
                        address.getBuildingNumber(),
                        address.getApartmentNumber()
                )
                .map(Address::of)
                .orElse(null);
    }

    @Override
    public Long insert(final Address address)
    {
        final var entity = AddressEntity.of(address);

        return addressRepositoryDao.saveAndFlush(entity).getAddressId();
    }

    @Override
    public void delete(final Long addressId)
    {
        final var address = findById(addressId);

        addressRepositoryDao.deleteById(address.getAddressId());
    }
}
