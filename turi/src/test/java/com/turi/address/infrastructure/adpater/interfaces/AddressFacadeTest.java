package com.turi.address.infrastructure.adpater.interfaces;

import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.RestControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RestControllerTest
class AddressFacadeTest
{
    @Autowired(required = false)
    private AddressFacade facade;

    @Test
    void testAddress_GetAddressById()
    {
        final var address = mockAddress();

        final var result = facade.getAddressById(String.valueOf(address.getAddressId()));

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertThat(result.getBody()).isEqualTo(address);
    }

    @Test
    void testAddress_GetAddressById_IdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.getAddressById(null));
    }

    @Test
    void testAddress_GetAddressById_NotFound()
    {
        assertThrows(AddressNotFoundException.class, () -> facade.getAddressById(String.valueOf(mockNewAddress().getAddressId())));
    }

    @Test
    void testAddress_GetAddressByAddress()
    {
        final var address = mockAddress();

        final var result = facade.getAddressByAddress(address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber());

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_GetAddressByAddress_NotFound()
    {
        final var address = mockNewAddress();

        final var result = facade.getAddressByAddress(address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber());

        assertNull(result);
    }

    @Test
    void testAddress_GetAddressByAddress_WithoutRequiredCountryField()
    {
        final var address = mockAddress();

        address.setCountry(null);

        assertThrows(InvalidAddressException.class, () -> facade.getAddressByAddress(address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber()));
    }

    @Test
    void testAddress_GetAddressByAddress_WithoutRequiredCityField()
    {
        final var address = mockAddress();

        address.setCity(null);

        assertThrows(InvalidAddressException.class, () -> facade.getAddressByAddress(address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber()));
    }

    @Test
    void testAddress_GetAddressByAddress_WithoutRequiredZipCodeField()
    {
        final var address = mockAddress();

        address.setZipCode(null);

        assertThrows(InvalidAddressException.class, () -> facade.getAddressByAddress(address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber()));
    }

    @Test
    void testAddress_GetAddressByAddress_WithoutRequiredStreetField()
    {
        final var address = mockAddress();

        address.setStreet(null);

        assertThrows(InvalidAddressException.class, () -> facade.getAddressByAddress(address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber()));
    }

    @Test
    void testAddress_GetAddressByAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = mockAddress();

        address.setBuildingNumber(null);

        assertThrows(InvalidAddressException.class, () -> facade.getAddressByAddress(address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber()));
    }

    @Test
    void testAddress_CreateAddress()
    {
        final var address = mockNewAddress();

        final var result = facade.createAddress(address);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertThat(result.getBody()).isEqualTo(address);
    }

    @Test
    void testAddress_CreateAddress_AddressIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.createAddress(null));
    }

    @Test
    void testAddress_CreateAddress_Exists()
    {
        final var address = mockAddress();

        final var result = facade.createAddress(address);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertThat(result.getBody()).isEqualTo(address);
    }

    @ParameterizedTest
    @CsvSource({"01-10", "01000", "0-1000", "0001-1", "-10000"})
    void testAddress_CreateAddress_InvalidZipCode(final String zipCode)
    {
        final var address = mockNewAddress();

        address.setZipCode(zipCode);

        assertThrows(BadRequestParameterException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredCountryField()
    {
        final var address = mockNewAddress();

        address.setCountry(null);

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredCityField()
    {
        final var address = mockNewAddress();

        address.setCity(null);

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredZipCodeField()
    {
        final var address = mockNewAddress();

        address.setZipCode(null);

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredStreetField()
    {
        final var address = mockNewAddress();

        address.setStreet(null);

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = mockNewAddress();

        address.setBuildingNumber(null);

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_DeleteById()
    {
        final var address = facade.getAddressById(String.valueOf(mockAddress().getAddressId())).getBody();

        assertNotNull(address);

        facade.deleteAddressById(address.getAddressId());

        assertThrows(AddressNotFoundException.class, () -> facade.getAddressById(String.valueOf(address.getAddressId())));
    }

    @Test
    void testAddress_DeleteById_NothingToDelete()
    {
        assertThrows(AddressNotFoundException.class, () -> facade.deleteAddressById(mockNewAddress().getAddressId()));
    }

    @Test
    void testAddress_DeleteById_IdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.deleteAddressById(null));
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
