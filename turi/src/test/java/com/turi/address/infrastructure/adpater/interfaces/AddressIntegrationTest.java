package com.turi.address.infrastructure.adpater.interfaces;

import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.testhelper.annotation.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class AddressIntegrationTest
{
    @Autowired(required = false)
    private AddressFacade facade;

    @Test
    void testAddress_getByAddress()
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

        final var result = facade.getByAddress(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_getByAddress_NotFound()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("2")
                .build();

        final var result = facade.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredCountryField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.getByAddress(address));
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredCityField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.getByAddress(address));
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredZipCodeField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.getByAddress(address));
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredStreetField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.getByAddress(address));
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.getByAddress(address));
    }

    @Test
    void testAddress_createAddress()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("2")
                .build();

        final var result = facade.createAddress(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_createAddress_Exists()
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

        final var result = facade.createAddress(address);

        assertNotNull(result);
        assertThat(result).isEqualTo(address);
    }

    @Test
    void testAddress_createAddress_WithoutRequiredCountryField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_createAddress_WithoutRequiredCityField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_createAddress_WithoutRequiredZipCodeField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_createAddress_WithoutRequiredStreetField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }

    @Test
    void testAddress_createAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = Address.builder()
                .withAddressId(1L)
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withApartmentNumber(10)
                .build();

        assertThrows(InvalidAddressException.class, () -> facade.createAddress(address));
    }
}
