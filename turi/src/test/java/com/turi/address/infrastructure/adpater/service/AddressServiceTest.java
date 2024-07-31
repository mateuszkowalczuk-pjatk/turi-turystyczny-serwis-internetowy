package com.turi.address.infrastructure.adpater.service;

import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.exception.InvalidAddressException;
import com.turi.address.domain.model.Address;
import com.turi.address.domain.port.AddressRepository;
import com.turi.address.domain.port.AddressService;
import com.turi.address.infrastructure.adapter.service.AddressServiceImpl;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class AddressServiceTest
{
    @Autowired
    private AddressRepository repository;

    private AddressService service;

    @BeforeEach
    void setup()
    {
        service = new AddressServiceImpl(repository);
    }

    @Test
    void testAddress_getById()
    {
        final var result = service.getById(1L);

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
    void testAddress_getById_NotFound()
    {
        assertThrows(AddressNotFoundException.class, () -> service.getById(2L));
    }

    @Test
    void testAddress_getById_IdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.getById(null));
    }

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

        final var result = service.getByAddress(address);

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

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredCountryField()
    {
        final var address = Address.builder()
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .build();

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredCityField()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .build();

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredZipCodeField()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Warszawa")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .build();

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredStreetField()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withBuildingNumber("1")
                .build();

        final var result = service.getByAddress(address);

        assertNull(result);
    }

    @Test
    void testAddress_getByAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01000")
                .withStreet("Warszawska")
                .build();

        final var result = service.getByAddress(address);

        assertNull(result);
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

        final var result = service.createAddress(address);

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

        final var result = service.createAddress(address);

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

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
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

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
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

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
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

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
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

        assertThrows(InvalidAddressException.class, () -> service.createAddress(address));
    }
}
