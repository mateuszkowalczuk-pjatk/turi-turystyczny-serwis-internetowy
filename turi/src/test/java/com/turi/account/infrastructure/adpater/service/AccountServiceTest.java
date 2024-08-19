package com.turi.account.infrastructure.adpater.service;

import com.turi.account.domain.exception.AccountNotFoundException;
import com.turi.account.domain.exception.AccountUniqueAddressException;
import com.turi.account.domain.exception.AccountUniquePhoneNumberException;
import com.turi.account.domain.exception.InvalidAccountException;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.model.Gender;
import com.turi.account.domain.port.AccountService;
import com.turi.address.domain.model.Address;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.testhelper.annotation.ServiceTest;
import com.turi.user.domain.model.User;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ServiceTest
public class AccountServiceTest
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
        final var account = mockNewAccount();

        assertThrows(AccountNotFoundException.class, () -> service.getById(account.getAccountId()));
    }

    @Test
    void testAccount_GetById_IdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.getById(null));
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
        final var account = mockNewAccount();

        final var result = service.getByUserId(account.getUserId());

        assertNull(result);
    }

    @Test
    void testAccount_GetByUserId_IdIsNull()
    {
        assertThrows(BadRequestParameterException.class, () -> service.getByUserId(null));
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
    void testAccount_CreateAccount_WithoutRequiredUserIdField()
    {
        final var account = Account.builder()
                .withAccountType(AccountType.NORMAL)
                .build();

        assertThrows(InvalidAccountException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_WithoutRequiredAccountTypeField()
    {
        final var account = Account.builder()
                .withUserId(1L)
                .build();

        assertThrows(InvalidAccountException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_UniqueUserId()
    {
        final var account = mockNewAccount();

        account.setUserId(mockAccount().getUserId());

        assertThrows(ConstraintViolationException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_UniqueAddressId()
    {
        final var account = mockNewAccount();

        account.setAddressId(mockAccount().getAddressId());

        assertThrows(ConstraintViolationException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_CreateAccount_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        account.setPhoneNumber(mockAccount().getPhoneNumber());

        assertThrows(ConstraintViolationException.class, () -> service.createAccount(account));
    }

    @Test
    void testAccount_UpdateAccount()
    {
        final var account = mockAccount();

        account.setFirstName("Marek");

        service.updateAccount(account.getAccountId(), account);

        final var result = service.getById(account.getAccountId());

        assertNotNull(result);
        assertThat(result.getAccountId()).isEqualTo(account.getAccountId());
        assertThat(result.getUserId()).isEqualTo(account.getUserId());
        assertThat(result.getAddressId()).isEqualTo(account.getAddressId());
        assertThat(result.getAccountType()).isEqualTo(account.getAccountType());
        assertThat(result.getFirstName()).isEqualTo(account.getFirstName());
        assertThat(result.getLastName()).isEqualTo(account.getLastName());
        assertThat(result.getBirthDate()).isEqualTo(account.getBirthDate());
        assertThat(result.getPhoneNumber()).isEqualTo(account.getPhoneNumber());
        assertThat(result.getGender()).isEqualTo(account.getGender());
    }

    @Test
    void testAccount_UpdateAccount_AccountNotFound()
    {
        final var account = mockNewAccount();

        assertThrows(AccountNotFoundException.class, () -> service.updateAccount(account.getAccountId(), account));
    }

    @Test
    void testAccount_UpdateAccount_UniqueAddress()
    {
        final var account = mockNewAccount();

        final var result = service.createAccount(account);

        result.setAddressId(mockAccount().getAddressId());

        assertThrows(AccountUniqueAddressException.class, () -> service.updateAccount(result.getAccountId(), result));
    }

    @Test
    void testAccount_UpdateAccount_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        final var result = service.createAccount(account);

        result.setPhoneNumber(mockAccount().getPhoneNumber());

        assertThrows(AccountUniquePhoneNumberException.class, () -> service.updateAccount(result.getAccountId(), result));
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
