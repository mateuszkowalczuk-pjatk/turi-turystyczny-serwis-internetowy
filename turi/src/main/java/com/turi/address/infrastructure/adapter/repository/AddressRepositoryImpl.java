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
    private final AddressRepositoryDao repositoryDao;

    @Override
    public Address findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(Address::of)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

    @Override
    public Address findByAddress(final Address address)
    {
        return repositoryDao
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

        return repositoryDao.saveAndFlush(entity).getAddressId();
    }

    @Override
    public void delete(final Long id)
    {
        repositoryDao.deleteById(id);
    }
}
