package com.turi.account.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.model.Gender;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.rest.ErrorCode;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import com.turi.user.domain.model.User;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@RestControllerTest
class AccountRestControllerTest extends AbstractRestControllerIntegrationTest
{
    @Autowired(required = false)
    private AccountFacade facade;

    @Autowired(required = false)
    private AddressFacade addressFacade;

    @Autowired(required = false)
    private UserFacade userFacade;

    @Test
    void testAccount_IsAddressExists()
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountAddressExists")
                .queryParam("country", "Polska")
                .queryParam("city", "Warszawa")
                .queryParam("zipCode", "01-000")
                .queryParam("street", "Warszawska")
                .queryParam("buildingNumber", "1")
                .queryParam("apartmentNumber", "10")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsAddressExists_NotExists()
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountAddressExists")
                .queryParam("country", "Polska")
                .queryParam("city", "Krakow")
                .queryParam("zipCode", "02-000")
                .queryParam("street", "Krakowska")
                .queryParam("buildingNumber", "1")
                .queryParam("apartmentNumber", "1")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsAddressExists_NotExistsForAccount()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Krakow")
                .withZipCode("02-000")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build();

        final var insertedAddress = addressFacade.createAddress(address);

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountAddressExists")
                .queryParam("country", insertedAddress.getCountry())
                .queryParam("city", insertedAddress.getCity())
                .queryParam("zipCode", insertedAddress.getZipCode())
                .queryParam("street", insertedAddress.getStreet())
                .queryParam("buildingNumber", insertedAddress.getBuildingNumber())
                .queryParam("apartmentNumber", insertedAddress.getApartmentNumber())
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsAddressExists_WithoutOptionalApartmentNumber()
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountAddressExists")
                .queryParam("country", "Polska")
                .queryParam("city", "Krakow")
                .queryParam("zipCode", "02-000")
                .queryParam("street", "Krakowska")
                .queryParam("buildingNumber", "1")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsPhoneNumberExists()
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountPhoneNumberExists")
                .queryParam("phoneNumber", "710212453")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsPhoneNumberExists_NotExists()
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountPhoneNumberExists")
                .queryParam("phoneNumber", "927182948")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_UpdateAccount()
    {
        final var account = mockAccount();

        account.setFirstName(mockNewAccount().getFirstName());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/updateAccount/{id}")
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getAccountId()).isEqualTo(account.getAccountId());
        assertThat(result.getBody().getUserId()).isEqualTo(account.getUserId());
        assertThat(result.getBody().getAddressId()).isEqualTo(account.getAddressId());
        assertThat(result.getBody().getAccountType()).isEqualTo(account.getAccountType());
        assertThat(result.getBody().getFirstName()).isEqualTo(account.getFirstName());
        assertThat(result.getBody().getLastName()).isEqualTo(account.getLastName());
        assertThat(result.getBody().getBirthDate()).isEqualTo(account.getBirthDate());
        assertThat(result.getBody().getPhoneNumber()).isEqualTo(account.getPhoneNumber());
        assertThat(result.getBody().getGender()).isEqualTo(account.getGender());
    }

    @Test
    void testAccount_UpdateAccount_AccountNotFound()
    {
        final var account = mockAccount();

        account.setAccountId(mockNewAccount().getAccountId());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/updateAccount/{accountId}")
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_UpdateAccount_UniqueAddress()
    {
        final var account = mockNewAccount();

        final var createdAccount = facade.createAccount(account);

        createdAccount.setAddressId(mockAccount().getAddressId());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/updateAccount/{accountId}")
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.postForEntity(uri, account, ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_UpdateAccount_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        final var createdAccount = facade.createAccount(account);

        createdAccount.setPhoneNumber(mockAccount().getPhoneNumber());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/updateAccount/{accountId}")
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.postForEntity(uri, account, ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    private Account mockAccount()
    {
        return Account.builder()
                .withAccountId(1L)
                .withUserId(1L)
                .withAddressId(1L)
                .withAccountType(AccountType.NORMAL)
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withBirthDate(LocalDate.of(2000,1, 1))
                .withPhoneNumber("710212453")
                .withGender(Gender.MALE)
                .build();
    }

    private Account mockNewAccount()
    {
        final var user = User.builder()
                .withUsername("Marek")
                .withEmail("marek@gmail.com")
                .withPassword("MarekNowak123!")
                .build();

        final var userId = userFacade.createUser(user).getUserId();

        return Account.builder()
                .withAccountId(2L)
                .withUserId(userId)
                .withAccountType(AccountType.NORMAL)
                .build();
    }
}
