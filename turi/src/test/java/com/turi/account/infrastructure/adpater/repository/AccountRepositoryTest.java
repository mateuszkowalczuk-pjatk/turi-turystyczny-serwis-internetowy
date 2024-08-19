package com.turi.account.infrastructure.adpater.repository;

import com.turi.account.domain.exception.AccountNotFoundException;
import com.turi.account.domain.exception.InvalidAccountException;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.model.Gender;
import com.turi.account.domain.port.AccountRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import com.turi.user.domain.model.User;
import com.turi.user.domain.port.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
public class AccountRepositoryTest
{
    @Autowired
    private AccountRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testAccount_FindAll()
    {
        final var result = repository.findAll();

        final var account = mockAccount();

        assertNotNull(result);
        assertThat(result).isEqualTo(List.of(account));
    }

    @Test
    void testAccount_FindAll_NothingFound()
    {
        final var accounts = repository.findAll();

        accounts.forEach(account -> repository.delete(account.getAccountId()));

        final var result = repository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void testAccount_FindById()
    {
        final var account = mockAccount();

        final var result = repository.findById(account.getAccountId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_FindById_NotFound()
    {
        final var account = mockNewAccount();

        assertThrows(AccountNotFoundException.class, () -> repository.findById(account.getAccountId()));
    }

    @Test
    void testAccount_FindByUserId()
    {
        final var account = mockAccount();

        final var result = repository.findByUserId(account.getUserId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_FindUserId_NotFound()
    {
        final var account = mockNewAccount();

        final var result = repository.findByUserId(account.getUserId());

        assertNull(result);
    }

    @Test
    void testAccount_FindByAddressId()
    {
        final var account = mockAccount();

        final var result = repository.findByAddressId(account.getAddressId());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_FindAddressId_NotFound()
    {
        final var account = mockNewAccount();

        final var result = repository.findByAddressId(account.getAddressId());

        assertNull(result);
    }

    @Test
    void testAccount_FindByPhoneNumber()
    {
        final var account = mockAccount();

        final var result = repository.findByPhoneNumber(account.getPhoneNumber());

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_FindByPhoneNumber_NotFound()
    {
        final var account = mockNewAccount();

        final var result = repository.findByPhoneNumber(account.getPhoneNumber());

        assertNull(result);
    }

    @Test
    void testAccount_Insert()
    {
        final var account = mockNewAccount();

        final var accountId = repository.insert(account);

        final var result = repository.findById(accountId);

        assertNotNull(result);
        assertThat(result).isEqualTo(account);
    }

    @Test
    void testAccount_Insert_WithoutRequiredUserIdField()
    {
        final var account = Account.builder()
                .withAccountType(AccountType.NORMAL)
                .build();

        assertThrows(InvalidAccountException.class, () -> repository.insert(account));
    }

    @Test
    void testAccount_Insert_WithoutRequiredAccountTypeField()
    {
        final var account = Account.builder()
                .withUserId(1L)
                .build();

        assertThrows(InvalidAccountException.class, () -> repository.insert(account));
    }

    @Test
    void testAccount_Insert_UniqueUserId()
    {
        final var account = mockNewAccount();

        account.setUserId(mockAccount().getUserId());

        assertThrows(ConstraintViolationException.class, () -> repository.insert(account));
    }

    @Test
    void testAccount_Insert_UniqueAddressId()
    {
        final var account = mockNewAccount();

        account.setAddressId(mockAccount().getAddressId());

        assertThrows(ConstraintViolationException.class, () -> repository.insert(account));
    }

    @Test
    void testAccount_Insert_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        account.setPhoneNumber(mockAccount().getPhoneNumber());

        assertThrows(ConstraintViolationException.class, () -> repository.insert(account));
    }

    @Test
    void testAccount_Update()
    {
        final var account = mockAccount();

        account.setAccountType(AccountType.PREMIUM);

        repository.update(account.getAccountId(), account);

        final var result = repository.findById(account.getAccountId());

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
    void testAccount_Update_AccountNotFound()
    {
        final var account = mockAccount();

        account.setAccountId(2L);

        repository.update(account.getAccountId(), account);

        assertThrows(AccountNotFoundException.class, () -> repository.findById(account.getAccountId()));
    }

    @Test
    void testAccount_Update_WithoutRequiredUserIdField()
    {
        final var account = mockAccount();

        account.setUserId(null);

        assertThrows(InvalidAccountException.class, () -> repository.update(account.getAccountId(), account));
    }

    @Test
    void testAccount_Update_WithoutRequiredAccountTypeField()
    {
        final var account = mockAccount();

        account.setAccountType(null);

        assertThrows(InvalidAccountException.class, () -> repository.update(account.getAccountId(), account));
    }

    @Test
    void testAccount_Update_UniqueUserId()
    {
        final var account = mockNewAccount();

        final var accountId = repository.insert(account);

        final var result = repository.findById(accountId);

        System.out.println(result);

        result.setUserId(mockAccount().getUserId());

        assertThrows(ConstraintViolationException.class, () -> repository.update(result.getAccountId(), result));
    }

    @Test
    void testAccount_Update_UniqueAddressId()
    {
        final var account = mockNewAccount();

        final var accountId = repository.insert(account);

        final var result = repository.findById(accountId);

        result.setAddressId(mockAccount().getAddressId());

        assertThrows(ConstraintViolationException.class, () -> repository.update(result.getAccountId(), result));
    }

    @Test
    void testAccount_Update_UniquePhoneNumber()
    {
        final var account = mockNewAccount();

        final var accountId = repository.insert(account);

        final var result = repository.findById(accountId);

        result.setPhoneNumber(mockAccount().getPhoneNumber());

        assertThrows(ConstraintViolationException.class, () -> repository.update(result.getAccountId(), result));
    }

    @Test
    void testAccount_Delete()
    {
        final var account = mockAccount();

        repository.delete(account.getAccountId());

        assertThrows(AccountNotFoundException.class, () -> repository.findById(account.getAccountId()));
    }

    @Test
    void testAccount_Delete_NothingToDelete()
    {
        final var account = mockNewAccount();

        assertThrows(AccountNotFoundException.class, () -> repository.delete(account.getAccountId()));
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

        final var userId = userRepository.insert(user);

        return Account.builder()
                .withAccountId(2L)
                .withUserId(userId)
                .withAccountType(AccountType.NORMAL)
                .build();
    }
}
