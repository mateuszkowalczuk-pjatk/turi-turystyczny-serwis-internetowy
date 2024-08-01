package com.turi.address.infrastructure.adpater.interfaces;

import com.turi.address.domain.model.Address;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RestControllerTest
class AddressRestControllerTest extends AbstractRestControllerIntegrationTest
{
    @Test
    void testAddress_createAddress()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("02-100")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/createAddress")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, address, Address.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getAddressId()).isEqualTo(address.getAddressId());
        assertThat(result.getBody().getCountry()).isEqualTo(address.getCountry());
        assertThat(result.getBody().getCity()).isEqualTo(address.getCity());
        assertThat(result.getBody().getZipCode()).isEqualTo(address.getZipCode());
        assertThat(result.getBody().getStreet()).isEqualTo(address.getStreet());
        assertThat(result.getBody().getBuildingNumber()).isEqualTo(address.getBuildingNumber());
    }

    @Test
    void testAddress_createAddress_Exists()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Warszawa")
                .withZipCode("01-000")
                .withStreet("Warszawska")
                .withBuildingNumber("1")
                .withApartmentNumber(10)
                .build();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/createAddress")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, address, Address.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getAddressId()).isEqualTo(1L);
        assertThat(result.getBody().getCountry()).isEqualTo(address.getCountry());
        assertThat(result.getBody().getCity()).isEqualTo(address.getCity());
        assertThat(result.getBody().getZipCode()).isEqualTo(address.getZipCode());
        assertThat(result.getBody().getStreet()).isEqualTo(address.getStreet());
        assertThat(result.getBody().getBuildingNumber()).isEqualTo(address.getBuildingNumber());
    }

    @Test
    void testAddress_createAddress_InvalidZipCode()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("02-20")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/createAddress")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, address, Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_createAddress_WithoutRequiredCountryField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCity("Kraków")
                .withZipCode("02-100")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/createAddress")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, address, Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_createAddress_WithoutRequiredCityField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withZipCode("02-100")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/createAddress")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, address, Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_createAddress_WithoutRequiredZipCodeField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Kraków")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/createAddress")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, address, Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_createAddress_WithoutRequiredStreetField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("02-100")
                .withBuildingNumber("1")
                .build();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/createAddress")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, address, Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_createAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("02-100")
                .withStreet("Krakowska")
                .build();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/createAddress")
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, address, Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }
}
