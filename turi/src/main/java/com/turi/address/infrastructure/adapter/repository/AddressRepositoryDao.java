package com.turi.address.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepositoryDao extends JpaRepository<AddressEntity, Long>
{
    Optional<AddressEntity> findByCountryAndCityAndZipCodeAndStreetAndBuildingNumberAndApartmentNumber(final String country,
                                                                                                       final String city,
                                                                                                       final String zipCode,
                                                                                                       final String street,
                                                                                                       final String buildingNumber,
                                                                                                       final Integer apartmentNumber);
}
