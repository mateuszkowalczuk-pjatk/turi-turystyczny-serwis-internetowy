package com.turi.address.infrastructure.adpater.repository;

import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class AddressRepositoryTest
{
    @Autowired
    private AddressRepository repository;

    @Test
    void testAddress_FindById()
    {
        final var result = repository.findById(1L);

        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_FindById_NotFound()
    {
        assertThrows(AddressNotFoundException.class, () -> repository.findById(2L));
    }

    @Test
    void testAddress_FindByAddress()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        final var result = repository.findByAddress(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_FindByAddress_NotFound()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("2")
                .build();

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredCountryField()
    {
        final var address = Address.builder()
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .build();

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredCityField()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .build();

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredZipCodeField()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Warszawa")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .build();

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredStreetField()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withBuildingNumber("1")
                .build();

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .build();

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_Insert()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("2")
                .withApartmentNumber(1)
                .build();

        final var addressId = repository.insert(address);

        final var result = repository.findById(addressId);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_Insert_WithoutRequiredCountryField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("2")
                .withApartmentNumber(1)
                .build();

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutRequiredCityField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("2")
                .withApartmentNumber(1)
                .build();

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutRequiredZipCodeField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withStreet("Warszawska")
                .withBuildingNumber("2")
                .withApartmentNumber(1)
                .build();

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutRequiredStreetField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withBuildingNumber("2")
                .withApartmentNumber(1)
                .build();

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutRequiredBuildingNumberField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withApartmentNumber(1)
                .build();

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutOptionalApartmentNumberField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("2")
                .build();

        final var addressId = repository.insert(address);

        final var result = repository.findById(addressId);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_Delete()
    {
        final var address = repository.findById(1L);

        repository.delete(address.getAddressId());

        assertThrows(AddressNotFoundException.class, () -> repository.findById(address.getAddressId()));
    }

    @Test
    void testAddress_Delete_NothingToDelete()
    {
        assertThrows(AddressNotFoundException.class, () -> repository.delete(2L));
    }
}
