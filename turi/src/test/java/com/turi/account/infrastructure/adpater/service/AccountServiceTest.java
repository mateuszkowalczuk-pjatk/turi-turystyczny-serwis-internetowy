package com.turi.account.infrastructure.adpater.service;

import com.turi.account.domain.exception.*;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.model.Gender;
import com.turi.account.domain.port.AccountService;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
public class AccountServiceTest
{
    @Autowired
    private AddressFacade addressFacade;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService service;

    @Test
    void testAccount_FindById()
    {
        final var account = mockAccount();

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_FindById_NotFound()
    {
        final var account = mockNewAccount();

        assertThrows(AccountNotFoundException.class, () -> service.getById(account.getAccountId()));
    }

    @Test
    void testAccount_FindById_IdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.getById(null));
    }

    @Test
    void testAccount_IsAddressExists()
    {
        final var result = service.isAddressExists("Polska", "Warszawa", "01-000", "Warszawska", "1", 10);

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsAddressExists_NotExists()
    {
        final var result = service.isAddressExists("Polska", "Kraków", "02-000", "Krakowska", "1", null);

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsAddressExists_NotExistsForAccount()
    {
        final var address = Address.builder()
                .withCountry("Polska")
                .withCity("Kraków")
                .withZipCode("02-000")
                .withStreet("Krakowska")
                .withBuildingNumber("1")
                .build();

        final var insertedAddress = addressFacade.createAddress(address);

        final var result = service.isAddressExists(insertedAddress.getCountry(),
                insertedAddress.getCity(),
                insertedAddress.getZipCode(),
                insertedAddress.getStreet(),
                insertedAddress.getBuildingNumber(),
                insertedAddress.getApartmentNumber());

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_GetByLogin()
    {
        final var account = mockAccount();

        final var result = service.getByLogin(account.getLogin());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_GetByLogin_NotFound()
    {
        final var account = mockNewAccount();

        final var result = service.getByLogin(account.getLogin());

        assertNull(result);
    }

    @Test
    void testAccount_IsLoginExists()
    {
        final var account = mockAccount();

        final var result = service.isLoginExists(account.getLogin());

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsLoginExists_NotExists()
    {
        final var account = mockNewAccount();

        final var result = service.isLoginExists(account.getLogin());

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_GetByEmail()
    {
        final var account = mockAccount();

        final var result = service.getByEmail(account.getEmail());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_GetByEmail_NotFound()
    {
        final var account = mockNewAccount();

        final var result = service.getByEmail(account.getEmail());

        assertNull(result);
    }

    @Test
    void testAccount_IsEmailExists()
    {
        final var account = mockAccount();

        final var result = service.isEmailExists(account.getEmail());

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsEmailExists_NotExists()
    {
        final var account = mockNewAccount();

        final var result = service.isEmailExists(account.getEmail());

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_IsPhoneNumberExists()
    {
        final var account = mockAccount();

        final var result = service.isPhoneNumberExists(account.getPhoneNumber());

        assertNotNull(result);
        assertTrue(result);
    }

    @Test
    void testAccount_IsPhoneNumber_NotExists()
    {
        final var account = mockNewAccount();

        final var result = service.isPhoneNumberExists(account.getPhoneNumber());

        assertNotNull(result);
        assertFalse(result);
    }

    @Test
    void testAccount_CreateAccount()
    {
        final var account = mockNewAccount();

        final var result = service.createAccount(account);

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_CreateAccount_UniqueLogin()
    {
        final var account = mockNewAccount();

        account.setLogin(mockAccount().getLogin());

        assertThrows(AccountUniqueLoginException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_UniqueEmail()
    {
        final var account = mockNewAccount();

        account.setEmail(mockAccount().getEmail());

        assertThrows(AccountUniqueEmailException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_WithoutRequiredAccountTypeField()
    {
        final var account = Account.builder()
                .withLogin("Marek")
                .withEmail("marek@gmail.com")
                .withPassword("MarekNowak123")
                .build();

        assertThrows(InvalidAccountException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_WithoutRequiredLoginField()
    {
        final var account = Account.builder()
                .withAccountType(AccountType.NORMAL)
                .withEmail("marek@gmail.com")
                .withPassword("MarekNowak123")
                .build();

        assertThrows(InvalidAccountException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_WithoutRequiredEmailField()
    {
        final var account = Account.builder()
                .withAccountType(AccountType.NORMAL)
                .withLogin("Marek")
                .withPassword("MarekNowak123")
                .build();

        assertThrows(InvalidAccountException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_WithoutRequiredPasswordField()
    {
        final var account = Account.builder()
                .withAccountType(AccountType.NORMAL)
                .withLogin("Marek")
                .withEmail("marek@gmail.com")
                .build();

        assertThrows(InvalidAccountException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_UpdateOwnerDetails()
    {
        final var account = mockAccount();

        account.setFirstName("Marek");

        service.updateOwnerDetails(account.getAccountId(), account);

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result.getAccountId()).isEqualTo(account.getAccountId());
        assertThat(result.getAddressId()).isEqualTo(account.getAddressId());
        assertThat(result.getFirstName()).isEqualTo(account.getFirstName());
        assertThat(result.getLastName()).isEqualTo(account.getLastName());
        assertThat(result.getBirthDate()).isEqualTo(account.getBirthDate());
        assertThat(result.getPhoneNumber()).isEqualTo(account.getPhoneNumber());
        assertThat(result.getGender()).isEqualTo(account.getGender());
    }

    @Test
    void testAccount_UpdateOwnerDetails_AccountNotFound()
    {
        final var account = mockAccount();

        account.setAccountId(2L);

        assertThrows(AccountNotFoundException.class, () -> service.updateOwnerDetails(account.getAccountId(), account));
    }

    @Test
    void testAccount_UpdateOwnerDetails_UniqueAddress()
    {
        final var account = mockNewAccount();

        final var result = service.createAccount(account);

        result.setAddressId(mockAccount().getAddressId());

        assertThrows(AccountUniqueAddressException.class, () -> service.updateOwnerDetails(result.getAccountId(), result));
    }

    @Test
    void testAccount_UpdateOwnerDetails_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        final var result = service.createAccount(account);

        result.setPhoneNumber(mockAccount().getPhoneNumber());

        assertThrows(AccountUniquePhoneNumberException.class, () -> service.updateOwnerDetails(result.getAccountId(), result));
    }

    @Test
    void testAccount_ResetPassword()
    {
        final var account = mockAccount();

        final var newPassword = "Janek12345";

        final var result = service.resetPassword(account.getAccountId(), newPassword);

        assertNotNull(result);
        assertTrue(passwordEncoder.matches(newPassword, result.getPassword()));
    }

    @Test
    void testAccount_ResetPassword_AccountNotFound()
    {
        final var account = mockNewAccount();

        final var newPassword = "Marek12345";

        assertThrows(AccountNotFoundException.class, () -> service.resetPassword(account.getAccountId(), newPassword));
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
                .withPassword("MarekNowak123")
                .build();
    }
}
