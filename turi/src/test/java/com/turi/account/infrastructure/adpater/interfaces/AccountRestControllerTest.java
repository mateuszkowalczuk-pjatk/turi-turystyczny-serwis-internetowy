package com.turi.account.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.model.Gender;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.authentication.domain.port.JwtService;
import com.turi.infrastructure.rest.ErrorCode;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.rest.AbstractRestControllerIntegrationTest;
import com.turi.testhelper.utils.ContextHelper;
import com.turi.user.domain.model.User;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Autowired(required = false)
    private JwtService jwtService;

    @Test
    void testAccount_GetAccountById()
    {
        final var account = mockAccount();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/getById")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(account.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(account);
    }

    @Test
    void testAccount_GetAccountById_ContextAccountIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/getById")
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_GetAccountById_NotFound()
    {
        final var account = mockNewAccount();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/getById")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(account.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_IsAccountAddressExists()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", "Polska")
                .queryParam("city", "Warszawa")
                .queryParam("zipCode", "01-000")
                .queryParam("street", "Warszawska")
                .queryParam("buildingNumber", "1")
                .queryParam("apartmentNumber", "10")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsAccountAddressExists_NotExists()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", "Polska")
                .queryParam("city", "Krakow")
                .queryParam("zipCode", "02-000")
                .queryParam("street", "Krakowska")
                .queryParam("buildingNumber", "1")
                .queryParam("apartmentNumber", "1")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsAccountAddressExists_NotExistsForAccount()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Krakow")
                .withZipCode("02-000")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build();

        final var insertedAddress = addressFacade.createAddress(address).getBody();

        assertNotNull(insertedAddress);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", insertedAddress.getCountry())
                .queryParam("city", insertedAddress.getCity())
                .queryParam("zipCode", insertedAddress.getZipCode())
                .queryParam("street", insertedAddress.getStreet())
                .queryParam("buildingNumber", insertedAddress.getBuildingNumber())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isPhoneNumberExists")
                .queryParam("phoneNumber", mockAccount().getPhoneNumber())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_NotExists()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isPhoneNumberExists")
                .queryParam("phoneNumber", "927182948")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_PhoneNumberIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isPhoneNumberExists")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_ActivateAccount()
    {
        final var mock = mockAccount();

        ContextHelper.setContextUserId(mock.getAccountId());

        facade.sendAccountActivateCode(mock.getAccountId());

        final var account = facade.getAccountById().getBody();

        assertNotNull(account);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/activate")
                .queryParam("code", account.getActivationCode())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(account.getAccountId(), AccountType.INACTIVE.getName()));

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertThat(facade.getAccountById().getBody().getAccountType()).isEqualTo(AccountType.NORMAL);
    }

    @Test
    void testAccount_ActivateAccount_ContextAccountIdIsNull()
    {
        final var account = mockAccount();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/activate")
                .queryParam("code", account.getActivationCode())
                .build().toUri();

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_ActivateAccount_CodeIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/activate")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_ActivateAccount_AccountNotFound()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/activate")
                .queryParam("code", mockAccount().getActivationCode())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockNewAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_ActivateAccount_InvalidActivationCode()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/activate")
                .queryParam("code", 111111)
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_ActivateAccount_ActivationCodeExpired()
    {
        final var account = mockAccount();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/activate")
                .queryParam("code", account.getActivationCode())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(account.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.postForEntity(uri, new HttpEntity<>(headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_UpdateAccount()
    {
        final var account = mockAccount();

        account.setFirstName(mockNewAccount().getFirstName());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .buildAndExpand(account.getAccountId())
                .toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(account);
    }

    @Test
    void testAccount_UpdateAccount_ContextAccountIdIsNull()
    {
        final var account = mockAccount();

        account.setFirstName(mockNewAccount().getFirstName());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .buildAndExpand(account.getAccountId())
                .toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_UpdateAccount_AccountIsNull()
    {
        final var account = mockAccount();

        account.setFirstName(mockNewAccount().getFirstName());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .buildAndExpand(account.getAccountId())
                .toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_UpdateAccount_AccountNotFound()
    {
        final var mock = mockNewAccount();

        final var account = mockAccount();

        account.setFirstName(mock.getFirstName());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mock.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_UpdateAccount_UniqueAddress()
    {
        final var account = mockNewAccount();

        final var createdAccount = facade.createAccount(account);

        createdAccount.setAddressId(mockAccount().getAddressId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(createdAccount.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(createdAccount, headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_UpdateAccount_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        final var createdAccount = facade.createAccount(account);

        createdAccount.setPhoneNumber(mockAccount().getPhoneNumber());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(createdAccount.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(createdAccount, headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    private Account mockAccount()
    {
        return Account.builder()
                .withAccountId(1L)
                .withUserId(1L)
                .withAddressId(1L)
                .withAccountType(AccountType.INACTIVE)
                .withActivationCode(123456)
                .withActivationCodeExpiresAt(LocalDateTime.of(2024, 1, 1, 12, 0, 0))
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
                .withEmail("marek@turi.com")
                .withPassword("MarekNowak123!")
                .build();

        final var userId = userFacade.createUser(user).getUserId();

        return Account.builder()
                .withAccountId(2L)
                .withUserId(userId)
                .withAccountType(AccountType.INACTIVE)
                .build();
    }
}
