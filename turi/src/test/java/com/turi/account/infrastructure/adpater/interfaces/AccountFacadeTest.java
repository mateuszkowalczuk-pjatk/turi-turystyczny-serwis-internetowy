package com.turi.account.infrastructure.adpater.interfaces;

import com.turi.account.domain.exception.*;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.model.Gender;
import com.turi.account.infrastructure.adapter.interfaces.AccountFacade;
import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.testhelper.annotation.RestControllerTest;
import com.turi.testhelper.utils.ContextHelper;
import com.turi.user.domain.model.User;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RestControllerTest
class AccountFacadeTest
{
    @Autowired(required = false)
    private AccountFacade facade;

    @Autowired(required = false)
    private AddressFacade addressFacade;

    @Autowired(required = false)
    private UserFacade userFacade;

    @Test
    void testAccount_GetAccountById()
    {
        final var account = mockAccount();

        ContextHelper.setContextUserId(account.getAccountId());

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_GetAccountById_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.getAccountById());
    }

    @Test
    void testAccount_GetAccountById_NotFound()
    {
        final var account = mockNewAccount();

        ContextHelper.setContextUserId(account.getAccountId());

        assertThrows(AccountNotFoundException.class, () -> facade.getAccountById());
    }

    @Test
    void testAccount_GetAccountByUserId()
    {
        final var account = mockAccount();

        final var result = facade.getAccountByUserId(account.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_GetAccountByUserId_NotFound()
    {
        final var account = mockNewAccount();

        assertThrows(BadRequestResponseException.class, () -> facade.getAccountByUserId(account.getUserId()));
    }

    @Test
    void testAccount_GetAccountByUserId_IdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.getAccountByUserId(null));
    }

    @Test
    void testAccount_IsAccountAddressExists_ExistsForOtherAccount()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("12-345")
                .withStreet("Warszawska")
                .withBuildingNumber("3B")
                .withApartmentNumber(10)
                .build();

        addressFacade.createAddress(address);

        final var account = mockNewAccount();
        account.setAddressId(address.getAddressId());

        facade.createAccount(account);

        ContextHelper.setContextUserId(mockAccount().getAccountId());

        final var result = facade.isAccountAddressExists(
                address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                String.valueOf(address.getApartmentNumber())
        ).getBody();

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsAccountAddressExists_ExistsButNotForOtherAccount()
    {
        final var address = Address.builder()
                .withAddressId(2L)
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("12-345")
                .withStreet("Warszawska")
                .withBuildingNumber("3B")
                .withApartmentNumber(10)
                .build();

        addressFacade.createAddress(address);

        ContextHelper.setContextUserId(mockAccount().getAccountId());

        final var result = facade.isAccountAddressExists(
                address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                String.valueOf(address.getApartmentNumber())
        ).getBody();

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsAccountAddressExists_ExistsForOurAccount()
    {
        ContextHelper.setContextUserId(mockAccount().getAccountId());

        final var result = facade.isAccountAddressExists("Polska", "Warszawa", "01-000", "Warszawska", "1", "10").getBody();

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsAccountAddressExists_NotExists()
    {
        ContextHelper.setContextUserId(mockAccount().getAccountId());

        final var result = facade.isAccountAddressExists("Polska", "Kraków", "02-000", "Krakowska", "1", null).getBody();

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsAccountAddressExists_ExistsAndAccountAddressNotExists()
    {
        final var account = mockNewAccount();

        facade.createAccount(account);

        ContextHelper.setContextUserId(account.getAccountId());

        final var result = facade.isAccountAddressExists("Polska", "Warszawa", "01-000", "Warszawska", "1", "10").getBody();

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsAccountAddressExists_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isAccountAddressExists("Polska", "Warszawa", "01-000", "Warszawska" , "1","10"));
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredCountryParameters()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isAccountAddressExists(null, "Warszawa", "01-000", "Warszawska" , "1","10"));
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredCityParameters()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isAccountAddressExists("Polska", null, "01-000", "Warszawska" , "1","10"));
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredZipCodeParameters()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isAccountAddressExists("Polska", "Warszawa", null, "Warszawska" , "1","10"));
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredStreetParameters()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isAccountAddressExists("Polska", "Warszawa", "01-000", null , "1","10"));
    }

    @Test
    void testAccount_IsAccountAddressExists_WithoutRequiredBuildingNumberParameters()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isAccountAddressExists("Polska", "Warszawa", "01-000", "Warszawska" , null,"10"));
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_ExistsForOtherAccount()
    {
        final var account = mockNewAccount();
        account.setPhoneNumber("635123567");

        facade.createAccount(account);

        ContextHelper.setContextUserId(mockAccount().getAccountId());

        final var result = facade.isAccountPhoneNumberExists(account.getPhoneNumber()).getBody();

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_ExistsForOurAccount()
    {
        final var account = mockAccount();

        ContextHelper.setContextUserId(account.getAccountId());

        final var result = facade.isAccountPhoneNumberExists(account.getPhoneNumber()).getBody();

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsAccountPhoneNumber_NotExists()
    {
        ContextHelper.setContextUserId(mockAccount().getAccountId());

        final var result = facade.isAccountPhoneNumberExists("543212453").getBody();

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsAccountPhoneNumber_ExistsAndAccountPhoneNumberNotExists()
    {
        final var account = mockNewAccount();

        facade.createAccount(account);

        ContextHelper.setContextUserId(account.getAccountId());

        final var result = facade.isAccountPhoneNumberExists(mockAccount().getPhoneNumber()).getBody();

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isAccountPhoneNumberExists(mockAccount().getPhoneNumber()));
    }

    @Test
    void testAccount_IsAccountPhoneNumberExists_WithoutRequiredPhoneNumberParameters()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.isAccountPhoneNumberExists(null));
    }

    @Test
    void testAccount_ActivateAccount()
    {
        final var account = mockAccount();

        facade.sendAccountActivateCode(account.getAccountId());

        final var response = Mockito.mock(HttpServletResponse.class);

        ContextHelper.setContextUserId(account.getAccountId());

        facade.activateAccount(String.valueOf(Objects.requireNonNull(facade.getAccountById().getBody()).getActivationCode()), response);

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.NORMAL);
    }

    @Test
    void testAccount_ActivateAccount_AccountNotFound()
    {
        ContextHelper.setContextUserId(mockNewAccount().getAccountId());

        final var response = Mockito.mock(HttpServletResponse.class);

        assertThrows(AccountNotFoundException.class, () -> facade.activateAccount(String.valueOf(mockAccount().getActivationCode()), response));
    }

    @Test
    void testAccount_ActivateAccount_InvalidActivationCode()
    {
        ContextHelper.setContextUserId(mockAccount().getAccountId());

        final var response = Mockito.mock(HttpServletResponse.class);

        final var account = mockAccount();

        facade.sendAccountActivateCode(account.getAccountId());

        final var accountForActivationCode = facade.getAccountById().getBody();

        assertNotNull(accountForActivationCode);

        final var activationCode = accountForActivationCode.getActivationCode();

        assertThrows(InvalidAccountActivationCode.class, () -> facade.activateAccount(String.valueOf(activationCode == 999999 ? activationCode - 1 : activationCode + 1), response));
    }

    @Test
    void testAccount_ActivateAccount_ActivationCodeExpired()
    {
        final var account = mockAccount();

        ContextHelper.setContextUserId(account.getAccountId());

        final var response = Mockito.mock(HttpServletResponse.class);

        assertThrows(AccountActivationCodeExpiredException.class, () -> facade.activateAccount(String.valueOf(account.getActivationCode()), response));
    }

    @Test
    void testAccount_ActivateAccount_ContextAccountIdIsNull()
    {
        final var response = Mockito.mock(HttpServletResponse.class);

        assertThrows(BadRequestParameterException.class, () -> facade.activateAccount(String.valueOf(mockAccount().getActivationCode()), response));
    }

    @Test
    void testAccount_ActivateAccount_WithoutRequiredCodeParameter()
    {
        final var response = Mockito.mock(HttpServletResponse.class);

        ContextHelper.setContextUserId(mockAccount().getAccountId());

        assertThrows(BadRequestParameterException.class, () -> facade.activateAccount(null, response));
    }

    @Test
    void testAccount_SendAccountActivateCode()
    {
        final var account = mockNewAccount();

        final var result = facade.createAccount(account);

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.INACTIVE);
        assertNotNull(result.getActivationCode());
        assertNotNull(result.getActivationCodeExpiresAt());
    }

    @Test
    void testAccount_SendAccountActivateCode_Resend()
    {
        final var account = mockAccount();

        facade.sendAccountActivateCode(account.getAccountId());

        ContextHelper.setContextUserId(account.getAccountId());

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.INACTIVE);
        assertNotNull(result.getActivationCode());
        assertNotNull(result.getActivationCodeExpiresAt());
    }

    @Test
    void testAccount_SendAccountActivateCode_AccountNotFound()
    {
        assertThrows(AccountNotFoundException.class, () -> facade.sendAccountActivateCode(mockNewAccount().getAccountId()));
    }

    @Test
    void testAccount_SendAccountActivateCode_ActivationCodeRecentlySent()
    {
        final var account = mockAccount();

        facade.sendAccountActivateCode(account.getAccountId());

        assertThrows(AccountActivationCodeRecentlySentException.class, () -> facade.sendAccountActivateCode(account.getAccountId()));
    }

    @Test
    void testAccount_SendAccountActivateCode_AccountAlreadyActivated()
    {
        final var account = mockAccount();

        facade.sendAccountActivateCode(account.getAccountId());

        ContextHelper.setContextUserId(account.getAccountId());

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);

        final var response = Mockito.mock(HttpServletResponse.class);

        facade.activateAccount(String.valueOf(result.getActivationCode()), response);

        assertThrows(BadRequestParameterException.class, () -> facade.sendAccountActivateCode(account.getAccountId()));
    }

    @Test
    void testAccount_CreateAccount()
    {
        final var account = mockNewAccount();

        final var result = facade.createAccount(account);

        assertNotNull(result);
        assertThat(result.getAccountId()).isEqualTo(account.getAccountId());
        assertThat(result.getUserId()).isEqualTo(account.getUserId());
        assertThat(result.getAccountType()).isEqualTo(account.getAccountType());
        assertNotNull(result.getActivationCode());
        assertNotNull(result.getActivationCodeExpiresAt());
    }

    @Test
    void testAccount_CreateAccount_AccountIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.createAccount(null));
    }

    @Test
    void testAccount_CreateAccount_WithoutRequiredUserIdField()
    {
        final var account = Account.builder()
                .withAccountType(mockNewAccount().getAccountType())
                .build();

        assertThrows(InvalidAccountException.class, () -> facade.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_WithoutRequiredAccountTypeField()
    {
        final var account = Account.builder()
                .withUserId(mockNewAccount().getUserId())
                .build();

        assertThrows(InvalidAccountException.class, () -> facade.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_UniqueUserId()
    {
        final var account = mockNewAccount();

        account.setUserId(mockAccount().getUserId());

        assertThrows(AccountUniqueUserIdException.class, () -> facade.createAccount(account));
    }

    @Test
    void testAccount_UpdateAccount()
    {
        final var account = mockAccount();

        account.setFirstName(mockAccount().getFirstName());

        ContextHelper.setContextUserId(account.getAccountId());

        facade.updateAccount(account);

        final var result = facade.getAccountByUserId(account.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_UpdateAccount_AccountNotFound()
    {
        final var account = mockNewAccount();

        ContextHelper.setContextUserId(account.getAccountId());

        assertThrows(AccountNotFoundException.class, () -> facade.updateAccount(account));
    }

    @Test
    void testAccount_UpdateAccount_UniqueAddress()
    {
        final var result = facade.createAccount(mockNewAccount());

        assertNotNull(result);

        ContextHelper.setContextUserId(result.getAccountId());

        result.setAddressId(mockAccount().getAddressId());

        assertThrows(AccountUniqueAddressException.class, () -> facade.updateAccount(result));
    }

    @Test
    void testAccount_Update_AddressNotFound()
    {
        final var result = facade.createAccount(mockNewAccount());

        result.setAddressId(2L);

        ContextHelper.setContextUserId(result.getAccountId());

        assertThrows(AccountUniqueAddressException.class, () -> facade.updateAccount(result));
    }

    @Test
    void testAccount_Update_WithoutAddress()
    {
        final var account = mockAccount();

        account.setAddressId(null);

        ContextHelper.setContextUserId(account.getAccountId());

        facade.updateAccount(account);

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_UpdateAccount_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        final var result = facade.createAccount(account);

        result.setPhoneNumber(mockAccount().getPhoneNumber());

        ContextHelper.setContextUserId(account.getAccountId());

        assertThrows(AccountUniquePhoneNumberException.class, () -> facade.updateAccount(result));
    }

    @Test
    void testAccount_UpdateAccount_WithoutPhoneNumber()
    {
        final var account = mockAccount();

        account.setPhoneNumber(null);

        ContextHelper.setContextUserId(account.getAccountId());

        facade.updateAccount(account);

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

        facade.updateAccount(account);

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result.getAddressId()).isEqualTo(account.getAddressId());
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

        facade.updateAccount(account);

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result).isEqualTo(account);

        assertThrows(AddressNotFoundException.class, () -> addressFacade.getAddressById(String.valueOf(addressId)));
    }

    @Test
    void testAccount_UpdateAccount_RemoveAddress()
    {
        final var account = mockAccount();

        final var addressId = account.getAddressId();

        account.setAddressId(null);

        ContextHelper.setContextUserId(account.getAccountId());

        facade.updateAccount(account);

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result).isEqualTo(account);

        assertThrows(AddressNotFoundException.class, () -> addressFacade.getAddressById(String.valueOf(addressId)));
    }

    @Test
    void testAccount_UpdateAccount_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.updateAccount(mockNewAccount()));
    }

    @Test
    void testAccount_UpdateAccount_AccountIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.updateAccount(null));
    }

    @Test
    void testAccount_UpdateAccountTypeToPremium()
    {
        final var account = mockAccount();

        ContextHelper.setContextUserId(account.getAccountId());

        facade.updateAccountTypeToPremium();

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.PREMIUM);
    }

    @Test
    void testAccount_UpdateAccountTypeToPremium_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.updateAccountTypeToPremium());
    }

    @Test
    void testAccount_UpdateAccountTypeToPremium_AccountNotFound()
    {
        ContextHelper.setContextUserId(mockNewAccount().getAccountId());

        assertThrows(AccountNotFoundException.class, () -> facade.updateAccountTypeToPremium());
    }

    @Test
    void testAccount_UpdateAccountTypeToNormal()
    {
        final var account = mockAccount();

        ContextHelper.setContextUserId(account.getAccountId());

        facade.updateAccountTypeToNormal();

        final var result = facade.getAccountById().getBody();

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.NORMAL);
    }

    @Test
    void testAccount_UpdateAccountTypeToNormal_ContextAccountIdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> facade.updateAccountTypeToNormal());
    }

    @Test
    void testAccount_UpdateAccountTypeToNormal_AccountNotFound()
    {
        ContextHelper.setContextUserId(mockNewAccount().getAccountId());

        assertThrows(AccountNotFoundException.class, () -> facade.updateAccountTypeToNormal());
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
