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
        final var address = mockAddress();

        final var result = repository.findById(address.getAddressId());

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_FindById_NotFound()
    {
        final var address = mockNewAddress();

        assertThrows(AddressNotFoundException.class, () -> repository.findById(address.getAddressId()));
    }

    @Test
    void testAddress_FindByAddress()
    {
        final var address = mockAddress();

        final var result = repository.findByAddress(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_FindByAddress_NotFound()
    {
        final var address = mockNewAddress();

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredCountryField()
    {
        final var address = mockAddress();

        address.setCountry(null);

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredCityField()
    {
        final var address = mockAddress();

        address.setCity(null);

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredZipCodeField()
    {
        final var address = mockAddress();

        address.setZipCode(null);

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredStreetField()
    {
        final var address = mockAddress();

        address.setStreet(null);

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_FindByAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = mockAddress();

        address.setBuildingNumber(null);

        final var result = repository.findByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_Insert()
    {
        final var address = mockNewAddress();

        final var addressId = repository.insert(address);

        final var result = repository.findById(addressId);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_Insert_WithoutRequiredCountryField()
    {
        final var address = mockNewAddress();

        address.setCountry(null);

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutRequiredCityField()
    {
        final var address = mockNewAddress();

        address.setCity(null);

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutRequiredZipCodeField()
    {
        final var address = mockNewAddress();

        address.setZipCode(null);

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutRequiredStreetField()
    {
        final var address = mockNewAddress();

        address.setStreet(null);

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutRequiredBuildingNumberField()
    {
        final var address = mockNewAddress();

        address.setBuildingNumber(null);

        assertThrows(InvalidAddressException.class, () -> repository.insert(address));
    }

    @Test
    void testAddress_Insert_WithoutOptionalApartmentNumberField()
    {
        final var address = mockNewAddress();

        address.setApartmentNumber(null);

        final var addressId = repository.insert(address);

        final var result = repository.findById(addressId);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_Delete()
    {
        final var address = repository.findById(mockAddress().getAddressId());

        repository.delete(address.getAddressId());

        assertThrows(AddressNotFoundException.class, () -> repository.findById(address.getAddressId()));
    }

    @Test
    void testAddress_Delete_NothingToDelete()
    {
        assertThrows(AddressNotFoundException.class, () -> repository.delete(mockNewAddress().getAddressId()));
    }

    private Address mockAddress()
    {
        return Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01-000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();
    }

    private Address mockNewAddress()
    {
        return Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01-000")
                .withStreet("Warszawska")
                .withBuildingNumber("2")
                .withApartmentNumber(1)
                .build();
    }
}
