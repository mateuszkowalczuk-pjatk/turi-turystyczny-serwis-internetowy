package com.turi.address.infrastructure.adpater.service;

import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressService;
import com.turi.infrastructure.exception.BadRequestParameterException;
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
        assertThrows(AddressNotFoundException.class, () -> service.getById(2L));
    }

    @Test
    void testAddress_GetById_IdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.getById(null));
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
    void testAddress_CreateAddress()
    {
        final var address = mockNewAddress();

        final var result = service.createAddress(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_CreateAddress_Exists()
    {
        final var address = mockAddress();

        final var result = service.createAddress(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredCountryField()
    {
        final var address = mockNewAddress();

        address.setCountry(null);

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredCityField()
    {
        final var address = mockNewAddress();

        address.setCity(null);

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredZipCodeField()
    {
        final var address = mockNewAddress();

        address.setZipCode(null);

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredStreetField()
    {
        final var address = mockNewAddress();

        address.setStreet(null);

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = mockNewAddress();

        address.setBuildingNumber(null);

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
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
