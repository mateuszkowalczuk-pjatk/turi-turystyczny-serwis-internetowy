package com.turi.account.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.model.Gender;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private PasswordEncoder passwordEncoder;

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
    void testAccount_IsLoginExists()
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountLoginExists")
                .queryParam("login", "Janek")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsLoginExists_NotExists()
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountLoginExists")
                .queryParam("login", "Marek")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsEmailExists()
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountEmailExists")
                .queryParam("email", "jan@gmail.com")
                .build().toUri();

        final var result = restTemplate.getForEntity(uri, Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsEmailExists_NotExists()
    {
        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/isAccountEmailExists")
                .queryParam("email", "marek@wp.com")
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
    void testAccount_UpdateOwnerDetails()
    {
        final var account = mockAccount();

        account.setFirstName("Marek");

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/updateAccountOwnerDetails/{id}")
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getAccountId()).isEqualTo(account.getAccountId());
        assertThat(result.getBody().getAddressId()).isEqualTo(account.getAddressId());
        assertThat(result.getBody().getFirstName()).isEqualTo(account.getFirstName());
        assertThat(result.getBody().getLastName()).isEqualTo(account.getLastName());
        assertThat(result.getBody().getBirthDate()).isEqualTo(account.getBirthDate());
        assertThat(result.getBody().getPhoneNumber()).isEqualTo(account.getPhoneNumber());
        assertThat(result.getBody().getGender()).isEqualTo(account.getGender());
    }

    @Test
    void testAccount_UpdateOwnerDetails_AccountNotFound()
    {
        final var account = mockAccount();

        account.setAccountId(2L);

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/updateAccountOwnerDetails/{accountId}")
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_UpdateOwnerDetails_UniqueAddress()
    {
        final var account = mockNewAccount();

        final var createdAccount = facade.createAccount(account);

        createdAccount.setAddressId(mockAccount().getAddressId());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/updateAccountOwnerDetails/{accountId}")
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.postForEntity(uri, account, Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_UpdateOwnerDetails_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        final var createdAccount = facade.createAccount(account);

        createdAccount.setPhoneNumber(mockAccount().getPhoneNumber());

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/updateAccountOwnerDetails/{accountId}")
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.postForEntity(uri, account, Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_ResetPassword()
    {
        final var account = mockAccount();

        account.setPassword("Janek123!");

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/resetAccountPassword/{id}")
                .queryParam("password", account.getPassword())
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertTrue(passwordEncoder.matches(account.getPassword(), result.getBody().getPassword()));
    }

    @ParameterizedTest
    @CsvSource({"Marek123", "marekmarek", "marek123", "Marek1", "marek123!"})
    void testAccount_ResetPassword_InvalidPassword(final String password)
    {
        final var account = mockAccount();

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/resetAccountPassword/{id}")
                .queryParam("password", password)
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_ResetPassword_AccountNotFound()
    {
        final var account = mockNewAccount();

        account.setPassword("Janek12345!");

        final URI uri = fromHttpUrl(getBaseUrl())
                .path("/resetAccountPassword/{id}")
                .queryParam("password", account.getPassword())
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    private Account mockAccount()
    {
        return Account.builder()
                .withAccountId(1L)
                .withAddressId(1L)
                .withAccountType(AccountType.NORMAL)
                .withLogin("Janek")
                .withEmail("jan@gmail.com")
                .withPassword("JanKowalski123")
                .withFirstName("Jan")
                .withLastName("Kowalski")
                .withBirthDate(LocalDate.of(2000,1, 1))
                .withPhoneNumber("710212453")
                .withGender(Gender.MALE)
                .build();
    }

    private Account mockNewAccount()
    {
        return Account.builder()
                .withAccountId(2L)
                .withAccountType(AccountType.NORMAL)
                .withLogin("Marek")
                .withEmail("marek@gmail.com")
                .withPassword("MarekNowak123!")
                .build();
    }
}
