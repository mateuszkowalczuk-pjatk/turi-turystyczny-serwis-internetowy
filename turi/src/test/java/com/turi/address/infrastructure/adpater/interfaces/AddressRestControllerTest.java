package com.turi.address.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.AccountType;
import com.turi.address.domain.model.Address;
import com.turi.authentication.domain.port.JwtService;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RestControllerTest
class AddressRestControllerTest extends AbstractRestControllerIntegrationTest
{
    @Autowired(required = false)
    private JwtService jwtService;

    @Test
    void testAddress_GetAddressById()
    {
        final var address = mockAddress();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/getById/{addressId}")
                .buildAndExpand(address.getAddressId())
                .toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Address.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(address);
    }

    @Test
    void testAddress_GetAddressById_IdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/getById/{addressId}")
                .buildAndExpand((Object) null)
                .toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_GetAddressById_NotFound()
    {
        final var address = mockNewAddress();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/getById/{addressId}")
                .buildAndExpand(address.getAddressId())
                .toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_CreateAddress()
    {
        final var address = mockNewAddress();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/create")
                .build().toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(address, headers), Address.class);

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
    void testAddress_CreateAddress_Exists()
    {
        final var address = mockAddress();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/create")
                .build().toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(address, headers), Address.class);

        System.out.println(result);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getAddressId()).isEqualTo(1L);
        assertThat(result.getBody().getCountry()).isEqualTo(address.getCountry());
        assertThat(result.getBody().getCity()).isEqualTo(address.getCity());
        assertThat(result.getBody().getZipCode()).isEqualTo(address.getZipCode());
        assertThat(result.getBody().getStreet()).isEqualTo(address.getStreet());
        assertThat(result.getBody().getBuildingNumber()).isEqualTo(address.getBuildingNumber());
    }

    @ParameterizedTest
    @CsvSource({"02-20", "02000", "0-2000", "0002-2", "20200-"})
    void testAddress_CreateAddress_InvalidZipCode(final String zipCode)
    {
        final var address = mockNewAddress();

        address.setZipCode(zipCode);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/create")
                .build().toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(address, headers), Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredCountryField()
    {
        final var address = mockNewAddress();

        address.setCountry(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/create")
                .build().toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(address, headers), Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredCityField()
    {
        final var address = mockNewAddress();

        address.setCity(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/create")
                .build().toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(address, headers), Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredZipCodeField()
    {
        final var address = mockNewAddress();

        address.setZipCode(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/create")
                .build().toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(address, headers), Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredStreetField()
    {
        final var address = mockNewAddress();

        address.setStreet(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/create")
                .build().toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(address, headers), Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAddress_CreateAddress_WithoutRequiredBuildingNumberField()
    {
        final var address = mockNewAddress();

        address.setBuildingNumber(null);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/address/create")
                .build().toUri();

        headers.set("Authorization", "Bearer " + getToken());

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(address, headers), Address.class);

        assertTrue(result.getStatusCode().is4xxClientError());
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

    private String getToken()
    {
        return jwtService.generateToken(1L, AccountType.NORMAL.getName());
    }
}
