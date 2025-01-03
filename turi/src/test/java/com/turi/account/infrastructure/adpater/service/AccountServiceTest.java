package com.turi.account.infrastructure.adpater.service;

import com.turi.account.domain.exception.*;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.model.Gender;
import com.turi.account.domain.port.AccountService;
import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.ServiceTest;
import com.turi.user.domain.model.User;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
class AccountServiceTest
{
    @Autowired
    private AccountService service;

    @Autowired
    private AddressFacade addressFacade;

    @Autowired
    private UserFacade userFacade;

    @Test
    void testAccount_GetById()
    {
        final var account = mockAccount();

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_GetById_NotFound()
    {
        assertThrows(AccountNotFoundException.class, () -> service.getById(mockNewAccount().getAccountId()));
    }

    @Test
    void testAccount_GetByUserId()
    {
        final var account = mockAccount();

        final var result = service.getByUserId(account.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_GetByUserId_NotFound()
    {
        assertNull(service.getByUserId(mockNewAccount().getUserId()));
    }

    @Test
    void testAccount_IsAddressExists_ExistsForOtherAccount()
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

        service.create(account);

        final var result = service.isAddressExists(
                mockAccount().getAccountId(),
                address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber()
        );

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsAddressExists_ExistsButNotForOtherAccount()
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

        final var result = service.isAddressExists(
                mockAccount().getAccountId(),
                address.getCountry(),
                address.getCity(),
                address.getZipCode(),
                address.getStreet(),
                address.getBuildingNumber(),
                address.getApartmentNumber()
        );

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsAddressExists_ExistsForOurAccount()
    {
        final var result = service.isAddressExists(mockAccount().getAccountId(), "Polska", "Warszawa", "01-000", "Warszawska", "1", 10);

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsAddressExists_NotExists()
    {
        final var result = service.isAddressExists(mockAccount().getAccountId(), "Polska", "Kraków", "02-000", "Krakowska", "1", null);

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsAddressExists_ExistsAndAccountAddressNotExists()
    {
        final var account = mockNewAccount();

        service.create(account);

        final var result = service.isAddressExists(account.getAccountId(), "Polska", "Warszawa", "01-000", "Warszawska", "1", 10);

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsPhoneNumberExists_ExistsForOtherAccount()
    {
        final var account = mockNewAccount();
        account.setPhoneNumber("635123567");

        service.create(account);

        final var result = service.isPhoneNumberExists(mockAccount().getAccountId(), account.getPhoneNumber());

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsPhoneNumberExists_ExistsForOurAccount()
    {
        final var account = mockAccount();

        final var result = service.isPhoneNumberExists(account.getAccountId(), account.getPhoneNumber());

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsPhoneNumber_NotExists()
    {
        final var result = service.isPhoneNumberExists(mockAccount().getAccountId(), "543212453");

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsPhoneNumber_ExistsAndAccountPhoneNumberNotExists()
    {
        final var account = mockNewAccount();

        service.create(account);

        final var result = service.isPhoneNumberExists(account.getAccountId(), mockAccount().getPhoneNumber());

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_Activate()
    {
        final var account = mockAccount();

        service.sendActivateCode(account.getAccountId());

        service.activate(account.getAccountId(), service.getById(account.getAccountId()).getActivationCode());

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.NORMAL);
    }

    @Test
    void testAccount_Activate_AccountNotFound()
    {
        assertThrows(AccountNotFoundException.class, () -> service.activate(mockNewAccount().getAccountId(), mockAccount().getActivationCode()));
    }

    @Test
    void testAccount_Activate_ActivationCodeNotFound()
    {
        final var account = mockNewAccount();

        service.create(account);

        service.activate(account.getAccountId(), service.getById(account.getAccountId()).getActivationCode());

        assertThrows(BadRequestParameterException.class, () -> service.activate(account.getAccountId(), account.getActivationCode()));
    }

    @Test
    void testAccount_Activate_InvalidActivationCode()
    {
        final var account = mockAccount();

        service.sendActivateCode(account.getAccountId());

        final var activationCode = service.getById(account.getAccountId()).getActivationCode();

        assertThrows(InvalidAccountActivationCode.class, () -> service.activate(account.getAccountId(), (activationCode == 999999 ? activationCode - 1 : activationCode + 1)));
    }

    @Test
    void testAccount_Activate_ActivationCodeExpired()
    {
        final var account = mockAccount();

        assertThrows(AccountActivationCodeExpiredException.class, () -> service.activate(account.getAccountId(), account.getActivationCode()));
    }

    @Test
    void testAccount_SendActivateCode()
    {
        final var result = service.create(mockNewAccount());

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.INACTIVE);
        assertNotNull(result.getActivationCode());
        assertNotNull(result.getActivationCodeExpiresAt());
    }

    @Test
    void testAccount_SendActivateCode_Resend()
    {
        final var account = mockAccount();

        service.sendActivateCode(account.getAccountId());

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.INACTIVE);
        assertNotNull(result.getActivationCode());
        assertNotNull(result.getActivationCodeExpiresAt());
    }

    @Test
    void testAccount_SendActivateCode_AccountNotFound()
    {
        assertThrows(AccountNotFoundException.class, () -> service.sendActivateCode(mockNewAccount().getAccountId()));
    }

    @Test
    void testAccount_SendActivateCode_ActivationCodeRecentlySent()
    {
        final var account = mockAccount();

        service.sendActivateCode(account.getAccountId());

        assertThrows(AccountActivationCodeRecentlySentException.class, () -> service.sendActivateCode(account.getAccountId()));
    }

    @Test
    void testAccount_SendActivateCode_AccountAlreadyActivated()
    {
        final var account = mockAccount();

        service.sendActivateCode(account.getAccountId());

        final var result = service.getById(account.getAccountId());

        service.activate(result.getAccountId(), result.getActivationCode());

        assertThrows(BadRequestParameterException.class, () -> service.sendActivateCode(account.getAccountId()));
    }

    @Test
    void testAccount_Create()
    {
        final var account = mockNewAccount();

        final var result = service.create(account);

        assertNotNull(result);
        assertThat(result.getAccountId()).isEqualTo(account.getAccountId());
        assertThat(result.getUserId()).isEqualTo(account.getUserId());
        assertThat(result.getAccountType()).isEqualTo(account.getAccountType());
        assertNotNull(result.getActivationCode());
        assertNotNull(result.getActivationCodeExpiresAt());
    }

    @Test
    void testAccount_Create_WithoutRequiredUserIdField()
    {
        final var account = Account.builder()
                .withAccountType(mockNewAccount().getAccountType())
                .build();

        assertThrows(InvalidAccountException.class, () -> service.create(account));
    }

    @Test
    void testAccount_Create_WithoutRequiredAccountTypeField()
    {
        final var account = Account.builder()
                .withUserId(mockNewAccount().getUserId())
                .build();

        assertThrows(InvalidAccountException.class, () -> service.create(account));
    }

    @Test
    void testAccount_Create_UniqueUserId()
    {
        final var account = mockNewAccount();

        account.setUserId(mockAccount().getUserId());

        assertThrows(AccountUniqueUserIdException.class, () -> service.create(account));
    }

    @Test
    void testAccount_Update()
    {
        final var account = mockAccount();

        account.setFirstName(mockNewAccount().getFirstName());

        service.update(account.getAccountId(), account);

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_Update_AccountNotFound()
    {
        final var account = mockNewAccount();

        assertThrows(AccountNotFoundException.class, () -> service.update(account.getAccountId(), account));
    }

    @Test
    void testAccount_Update_UniqueAddress()
    {
        final var result = service.create(mockNewAccount());

        result.setAddressId(mockAccount().getAddressId());

        assertThrows(AccountUniqueAddressException.class, () -> service.update(result.getAccountId(), result));
    }

    @Test
    void testAccount_Update_AddressNotFound()
    {
        final var result = service.create(mockNewAccount());

        result.setAddressId(2L);

        assertThrows(AccountUniqueAddressException.class, () -> service.update(result.getAccountId(), result));
    }

    @Test
    void testAccount_Update_WithoutAddress()
    {
        final var account = mockAccount();

        account.setAddressId(null);

        service.update(account.getAccountId(), account);

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_Update_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        final var result = service.create(account);

        result.setPhoneNumber(mockAccount().getPhoneNumber());

        assertThrows(AccountUniquePhoneNumberException.class, () -> service.update(result.getAccountId(), result));
    }

    @Test
    void testAccount_Update_WithoutPhoneNumber()
    {
        final var account = mockAccount();

        account.setPhoneNumber(null);

        service.update(account.getAccountId(), account);

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_Update_AddAddress()
    {
        final var account = mockNewAccount();

        service.create(account);

        final var address = addressFacade.createAddress(Address.builder()
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("02-000")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build()).getBody();

        assertNotNull(address);

        account.setAddressId(address.getAddressId());

        service.update(account.getAccountId(), account);

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result.getAddressId()).isEqualTo(account.getAddressId());
    }

    @Test
    void testAccount_Update_ChangeAddress()
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

        service.update(account.getAccountId(), account);

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);

        assertThrows(AddressNotFoundException.class, () -> addressFacade.getAddressById(String.valueOf(addressId)));
    }

    @Test
    void testAccount_Update_RemoveAddress()
    {
        final var account = mockAccount();

        final var addressId = account.getAddressId();

        account.setAddressId(null);

        service.update(account.getAccountId(), account);

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);

        assertThrows(AddressNotFoundException.class, () -> addressFacade.getAddressById(String.valueOf(addressId)));
    }

    @Test
    void testAccount_UpdateAccountTypeToPremium()
    {
        final var account = mockAccount();

        service.updateAccountTypeToPremium(account.getAccountId());

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.PREMIUM);
    }

    @Test
    void testAccount_UpdateAccountTypeToPremium_AccountNotFound()
    {
        assertThrows(AccountNotFoundException.class, () -> service.updateAccountTypeToPremium(mockNewAccount().getAccountId()));
    }

    @Test
    void testAccount_UpdateAccountTypeToNormal()
    {
        final var account = mockAccount();

        service.updateAccountTypeToNormal(account.getAccountId());

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result.getAccountType()).isEqualTo(AccountType.NORMAL);
    }

    @Test
    void testAccount_UpdateAccountTypeToNormal_AccountNotFound()
    {
        assertThrows(AccountNotFoundException.class, () -> service.updateAccountTypeToNormal(mockNewAccount().getAccountId()));
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
