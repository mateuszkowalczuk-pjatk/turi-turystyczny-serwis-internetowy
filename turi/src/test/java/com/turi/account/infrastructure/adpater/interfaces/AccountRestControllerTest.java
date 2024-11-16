package com.turi.account.infrastructure.adpater.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.model.Gender;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.address.domain.exception.AddressNotFoundException;
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
    void testAccount_IsAccountAddressExists_ExistsForOtherAccount()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Krakow")
                .withZipCode("12-345")
                .withStreet("Warszawska")
                .withBuildingNumber("3B")
                .withApartmentNumber(10)
                .build();

        addressFacade.createAddress(address);

        final var account = mockNewAccount();
        account.setAddressId(address.getAddressId());

        facade.createAccount(account);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", address.getCountry())
                .queryParam("city", address.getCity())
                .queryParam("zipCode", address.getZipCode())
                .queryParam("street", address.getStreet())
                .queryParam("buildingNumber", address.getBuildingNumber())
                .queryParam("apartmentNumber", address.getApartmentNumber())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsAccountAddressExists_ExistsButNotForOtherAccount()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Krakow")
                .withZipCode("12-345")
                .withStreet("Warszawska")
                .withBuildingNumber("3B")
                .withApartmentNumber(10)
                .build();

        addressFacade.createAddress(address);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", address.getCountry())
                .queryParam("city", address.getCity())
                .queryParam("zipCode", address.getZipCode())
                .queryParam("street", address.getStreet())
                .queryParam("buildingNumber", address.getBuildingNumber())
                .queryParam("apartmentNumber", address.getApartmentNumber())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsAccountAddressExists_ExistsForOurAccount()
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
        assertEquals(Boolean.FALSE, result.getBody());
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
    void testAccount_IsAccountAddressExists_ExistsAndAccountAddressNotExists()
    {
        final var account = mockNewAccount();

        facade.createAccount(account);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", "Polska")
                .queryParam("city", "Warszawa")
                .queryParam("zipCode", "01-000")
                .queryParam("street", "Warszawska")
                .queryParam("buildingNumber", "1")
                .queryParam("apartmentNumber", "10")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(account.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsAccountAddressExists_ContextAccountIdIsNull()
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

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredCountryParameter()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("city", "Warszawa")
                .queryParam("zipCode", "01-000")
                .queryParam("street", "Warszawska")
                .queryParam("buildingNumber", "1")
                .queryParam("apartmentNumber", "10")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredCityParameter()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", "Polska")
                .queryParam("zipCode", "01-000")
                .queryParam("street", "Warszawska")
                .queryParam("buildingNumber", "1")
                .queryParam("apartmentNumber", "10")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredZipCodeParameter()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", "Polska")
                .queryParam("city", "Warszawa")
                .queryParam("street", "Warszawska")
                .queryParam("buildingNumber", "1")
                .queryParam("apartmentNumber", "10")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredStreetParameter()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", "Polska")
                .queryParam("city", "Warszawa")
                .queryParam("zipCode", "01-000")
                .queryParam("buildingNumber", "1")
                .queryParam("apartmentNumber", "10")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredBuildingNumberParameter()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isAddressExists")
                .queryParam("country", "Polska")
                .queryParam("city", "Warszawa")
                .queryParam("zipCode", "01-000")
                .queryParam("street", "Warszawska")
                .queryParam("apartmentNumber", "10")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_ExistsForOtherAccount()
    {
        final var account = mockNewAccount();
        account.setPhoneNumber("635123567");

        facade.createAccount(account);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isPhoneNumberExists")
                .queryParam("phoneNumber", account.getPhoneNumber())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_ExistsForOurAccount()
    {
        final var account = mockAccount();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isPhoneNumberExists")
                .queryParam("phoneNumber", account.getPhoneNumber())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(account.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsAccountPhoneNumber_NotExists()
    {
        final var uri = fromHttpUrl(getBaseUrl())
            .path("/api/account/isPhoneNumberExists")
            .queryParam("phoneNumber", "543212453")
            .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.FALSE, result.getBody());
    }

    @Test
    void testAccount_IsAccountPhoneNumber_ExistsAndAccountPhoneNumberNotExists()
    {
        final var account = mockNewAccount();

        facade.createAccount(account);

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isPhoneNumberExists")
                .queryParam("phoneNumber", mockAccount().getPhoneNumber())
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(account.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Boolean.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(Boolean.TRUE, result.getBody());
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_ContextAccountIdIsNull()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/isPhoneNumberExists")
                .queryParam("phoneNumber", mockAccount().getPhoneNumber())
                .build().toUri();

        final var result = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), ErrorCode.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_WithoutRequiredPhoneNumberParameter()
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
        ContextHelper.setContextUserId(mockAccount().getAccountId());

        final var account = mockAccount();

        facade.sendAccountActivateCode(account.getAccountId());

        final var accountForActivationCode = facade.getAccountById().getBody();

        assertNotNull(accountForActivationCode);

        final var activationCode = accountForActivationCode.getActivationCode();

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/activate")
                .queryParam("code", (activationCode == 999999 ? activationCode - 1 : activationCode + 1))
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
    void testAccount_ActivateAccount_WithoutRequiredCodeParameter()
    {
        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/activate")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

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
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody()).isEqualTo(account);
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
        final var createdAccount = facade.createAccount(mockNewAccount());

        createdAccount.setAddressId(mockAccount().getAddressId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(createdAccount.getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(createdAccount, headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());
    }

    @Test
    void testAccount_Update_AddressNotFound()
    {
        final var account = facade.createAccount(mockNewAccount());

        account.setAddressId(2L);

        ContextHelper.setContextUserId(account.getAccountId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), Account.class);

        assertTrue(result.getStatusCode().is4xxClientError());

    }

    @Test
    void testAccount_Update_WithoutAddress()
    {
        final var account = mockAccount();

        account.setAddressId(null);

        ContextHelper.setContextUserId(account.getAccountId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), Account.class);

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
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

    @Test
    void testAccount_UpdateAccount_WithoutPhoneNumber()
    {
        final var account = mockAccount();

        account.setPhoneNumber(null);

        ContextHelper.setContextUserId(account.getAccountId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), Account.class);

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_UpdateAccount_AddAddress()
    {
        final var account = mockNewAccount();

        ContextHelper.setContextUserId(account.getAccountId());

        facade.createAccount(account);

        final var address = addressFacade.createAddress(Address.builder()
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("02-000")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build()).getBody();

        assertNotNull(address);

        account.setAddressId(address.getAddressId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getAddressId()).isEqualTo(account.getAddressId());
    }

    @Test
    void testAccount_UpdateAccount_ChangeAddress()
    {
        final var account = mockAccount();

        final var address = addressFacade.createAddress(Address.builder()
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("02-000")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build()).getBody();

        assertNotNull(address);

        final var addressId = account.getAddressId();

        account.setAddressId(address.getAddressId());

        ContextHelper.setContextUserId(account.getAccountId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getAddressId()).isEqualTo(account.getAddressId());

        assertThrows(AddressNotFoundException.class, () -> addressFacade.getAddressById(String.valueOf(addressId)));
    }

    @Test
    void testAccount_UpdateAccount_RemoveAddress()
    {
        final var account = mockAccount();

        final var addressId = account.getAddressId();

        account.setAddressId(null);

        ContextHelper.setContextUserId(account.getAccountId());

        final var uri = fromHttpUrl(getBaseUrl())
                .path("/api/account/update")
                .build().toUri();

        headers.set("Authorization", "Bearer " + jwtService.generateToken(mockAccount().getAccountId(), AccountType.NORMAL.getName()));

        final var result = restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(account, headers), Account.class);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertThat(result.getBody().getAddressId()).isEqualTo(account.getAddressId());

        assertThrows(AddressNotFoundException.class, () -> addressFacade.getAddressById(String.valueOf(addressId)));
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
    void testAccount_UpdateAccount_WithoutRequiredAccountBody()
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
