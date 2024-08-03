package com.turi.account.infrastructure.adapter.service;

import com.turi.account.domain.exception.AccountUniqueAddressException;
import com.turi.account.domain.exception.AccountUniqueEmailException;
import com.turi.account.domain.exception.AccountUniqueLoginException;
import com.turi.account.domain.exception.AccountUniquePhoneNumberException;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.port.AccountRepository;
import com.turi.account.domain.port.AccountService;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService
{
    private final AccountRepository accountRepository;
    private final AddressFacade addressFacade;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Account getById(final Long id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Account ID must not be null.");
        }

        return accountRepository.findById(id);
    }

    @Override
    public Boolean isAddressExists(final String country,
                                   final String city,
                                   final String zipCode,
                                   final String street,
                                   final String buildingNumber,
                                   final Integer apartmentNumber)
    {
        final var address = addressFacade.getByAddress(country, city, zipCode, street, buildingNumber, apartmentNumber);

        if (address == null)
        {
            return false;
        }

        return isAddressExists(address.getAddressId());
    }

    @Override
    public Account getByLogin(final String login)
    {
        return accountRepository.findByLogin(login);
    }

    @Override
    public Boolean isLoginExists(final String login)
    {
        return getByLogin(login) != null;
    }

    @Override
    public Account getByEmail(final String email)
    {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Boolean isEmailExists(final String email)
    {
        return getByEmail(email) != null;
    }

    @Override
    public Boolean isPhoneNumberExists(final String phoneNumber)
    {
        return accountRepository.findByPhoneNumber(phoneNumber) != null;
    }

    @Override
    public Account createAccount(final Account account)
    {
        if (isLoginExists(account.getLogin()))
        {
            throw new AccountUniqueLoginException(account.getLogin());
        }

        if (isEmailExists(account.getEmail()))
        {
            throw new AccountUniqueEmailException(account.getEmail());
        }

        final var accountId = accountRepository.insert(account);

        return getById(accountId);
    }

    @Override
    public Account updateOwnerDetails(final Long accountId, final Account account)
    {
        final var currentAccount = getById(accountId);

        if (account.getAddressId() != null && isAddressExists(account.getAddressId()) &&
                (currentAccount.getAddressId() == null || !currentAccount.getAddressId().equals(account.getAddressId())))
        {
            throw new AccountUniqueAddressException(account.getAddressId());
        }

        if (account.getPhoneNumber() != null && isPhoneNumberExists(account.getPhoneNumber()) &&
                (currentAccount.getPhoneNumber() == null || !currentAccount.getPhoneNumber().equals(account.getPhoneNumber())))
        {
            throw new AccountUniquePhoneNumberException(account.getPhoneNumber());
        }

        final var accountToUpdate = Account.builder()
                .withAddressId(account.getAddressId())
                .withAccountType(currentAccount.getAccountType())
                .withLogin(currentAccount.getLogin())
                .withEmail(currentAccount.getEmail())
                .withPassword(currentAccount.getPassword())
                .withFirstName(account.getFirstName())
                .withLastName(account.getLastName())
                .withBirthDate(account.getBirthDate())
                .withPhoneNumber(account.getPhoneNumber())
                .withGender(account.getGender())
                .build();

        accountRepository.update(accountId, accountToUpdate);

        return getById(accountId);
    }

    private Boolean isAddressExists(final Long addressId)
    {
        return accountRepository.findByAddressId(addressId) != null;
    }

    @Override
    public Account resetPassword(final Long accountId, final String password)
    {
        final var account = getById(accountId);

        account.setPassword(passwordEncoder.encode(password));

        accountRepository.update(accountId, account);

        return account;
    }
}
