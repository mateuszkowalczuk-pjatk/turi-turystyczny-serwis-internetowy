package com.turi.address.infrastructure.adpater.service;

import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressService;
import com.turi.testhelper.annotation.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class AddressServiceTest
{
    @Autowired
    private AddressService service;

    @Test
    void testAddress_GetById()
    {
        final var address = mockAddress();

        final var result = service.getById(address.getAddressId());

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_GetById_NotFound()
    {
        assertThrows(AddressNotFoundException.class, () -> service.getById(mockNewAddress().getAddressId()));
    }

    @Test
    void testAddress_GetByAddress()
    {
        final var address = mockAddress();

        final var result = service.getByAddress(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_GetByAddress_NotFound()
    {
        final var address = mockNewAddress();

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_GetByAddress_WithoutRequiredCountryField()
    {
        final var address = mockAddress();

        address.setCountry(null);

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_GetByAddress_WithoutRequiredCityField()
    {
        final var address = mockAddress();

        address.setCity(null);

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_GetByAddress_WithoutRequiredZipCodeField()
    {
        final var address = mockAddress();

        address.setZipCode(null);

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_GetByAddress_WithoutRequiredStreetField()
    {
        final var address = mockAddress();

        address.setStreet(null);

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_GetByAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = mockAddress();

        address.setBuildingNumber(null);

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_Create()
    {
        final var address = mockNewAddress();

        final var result = service.create(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_Create_Exists()
    {
        final var address = mockAddress();

        final var result = service.create(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_Create_WithoutRequiredCountryField()
    {
        final var address = mockNewAddress();

        address.setCountry(null);

        assertThrows(InvalidAddressException.class, () -> service.create(address));
    }

    @Test
    void testAddress_Create_WithoutRequiredCityField()
    {
        final var address = mockNewAddress();

        address.setCity(null);

        assertThrows(InvalidAddressException.class, () -> service.create(address));
    }

    @Test
    void testAddress_Create_WithoutRequiredZipCodeField()
    {
        final var address = mockNewAddress();

        address.setZipCode(null);

        assertThrows(InvalidAddressException.class, () -> service.create(address));
    }

    @Test
    void testAddress_Create_WithoutRequiredStreetField()
    {
        final var address = mockNewAddress();

        address.setStreet(null);

        assertThrows(InvalidAddressException.class, () -> service.create(address));
    }

    @Test
    void testAddress_Create_WithoutRequiredBuildingNumberField()
    {
        final var address = mockNewAddress();

        address.setBuildingNumber(null);

        assertThrows(InvalidAddressException.class, () -> service.create(address));
    }

    @Test
    void testAddress_DeleteById()
    {
        final var address = service.getById(mockAddress().getAddressId());

        service.deleteById(address.getAddressId());

        assertThrows(AddressNotFoundException.class, () -> service.getById(address.getAddressId()));
    }

    @Test
    void testAddress_DeleteById_NothingToDelete()
    {
        assertThrows(AddressNotFoundException.class, () -> service.deleteById(mockNewAddress().getAddressId()));
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
                .build();
    }
}
