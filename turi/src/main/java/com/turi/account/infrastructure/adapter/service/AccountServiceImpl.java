package com.turi.account.infrastructure.adapter.service;

import com.turi.account.domain.exception.*;
import com.turi.account.domain.model.Account;
import com.turi.account.domain.model.AccountType;
import com.turi.account.domain.port.AccountRepository;
import com.turi.account.domain.port.AccountService;
import com.turi.address.domain.exception.AddressNotFoundException;
import com.turi.address.infrastructure.adapter.interfaces.AddressFacade;
import com.turi.infrastructure.common.CodeGenerator;
import com.turi.infrastructure.common.EmailSender;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.user.infrastructure.adapter.interfaces.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService
{
    private final UserFacade userFacade;
    private final EmailSender emailSender;
    private final AddressFacade addressFacade;
    private final AccountRepository repository;

    @Override
    public Account getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public Account getByUserId(final Long userId)
    {
        return repository.findByUserId(userId);
    }

    @Override
    public Boolean isAddressExists(final Long accountId,
                                   final String country,
                                   final String city,
                                   final String zipCode,
                                   final String street,
                                   final String buildingNumber,
                                   final Integer apartmentNumber)
    {
        final var address = addressFacade.getAddressByAddress(country, city, zipCode, street, buildingNumber, apartmentNumber);

        final var accountAddressId = getById(accountId).getAddressId();

        if (address == null || (accountAddressId != null && accountAddressId.equals(address.getAddressId())))
        {
            return false;
        }

        return isAddressExists(address.getAddressId());
    }

    @Override
    public Boolean isPhoneNumberExists(final Long accountId, final String phoneNumber)
    {
        final var account = repository.findByPhoneNumber(phoneNumber);

        final var accountPhoneNumber = getById(accountId).getPhoneNumber();

        return account != null && (accountPhoneNumber == null || !accountPhoneNumber.equals(account.getPhoneNumber()));
    }

    @Override
    public void activate(final Long id, final Integer code)
    {
        final var account = getById(id);

        if (account.getActivationCode() == null)
        {
            throw new BadRequestParameterException("Account already activated.");
        }

        if (!account.getActivationCode().equals(code))
        {
            throw new InvalidAccountActivationCode();
        }

        if (account.getActivationCodeExpiresAt().isAfter(LocalDateTime.now()))
        {
            account.setAccountType(AccountType.NORMAL);
            account.setActivationCode(null);
            account.setActivationCodeExpiresAt(null);

            repository.update(id, account);
        }
        else
        {
            sendActivateCode(id);

            throw new AccountActivationCodeExpiredException();
        }
    }

    @Override
    public void sendActivateCode(final Long id)
    {
        final var account = getById(id);

        if (account.getActivationCodeExpiresAt() == null || account.getActivationCodeExpiresAt().minusMinutes(10).isBefore(LocalDateTime.now()))
        {
            if (!account.getAccountType().equals(AccountType.INACTIVE))
            {
                throw new BadRequestParameterException("Account already activated.");
            }

            final var code = CodeGenerator.generateCode();

            account.setActivationCode(code);
            account.setActivationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));

            repository.update(id, account);

            final var email = userFacade.getUserById(id).getEmail();

            emailSender.sendActivationEmail(email, "User account activation code.", code);
        }
        else
        {
            throw new AccountActivationCodeRecentlySentException();
        }
    }

    @Override
    public Account create(final Account account)
    {
        if (getByUserId(account.getUserId()) != null)
        {
            throw new AccountUniqueUserIdException(account.getUserId());
        }

        final var accountId = repository.insert(account);

        sendActivateCode(accountId);

        return getById(accountId);
    }

    @Override
    public Account update(final Long id, final Account account)
    {
        final var currentAccount = getById(id);

        if (account.getAddressId() != null)
        {
            try
            {
                final var address = addressFacade.getAddressById(String.valueOf(account.getAddressId())).getBody();

                if (address != null && isAddressExists(id, address.getCountry(), address.getCity(), address.getZipCode(), address.getStreet(), address.getBuildingNumber(), address.getApartmentNumber()))
                {
                    throw new AccountUniqueAddressException(account.getAddressId());
                }
            }
            catch (AddressNotFoundException ex)
            {
                throw new AccountUniqueAddressException(account.getAddressId());
            }
        }

        if (account.getPhoneNumber() != null && isPhoneNumberExists(id, account.getPhoneNumber()))
        {
            throw new AccountUniquePhoneNumberException(account.getPhoneNumber());
        }

        final var accountToUpdate = Account.builder()
                .withAddressId(account.getAddressId())
                .withAccountType(currentAccount.getAccountType())
                .withActivationCode(currentAccount.getActivationCode())
                .withActivationCodeExpiresAt(currentAccount.getActivationCodeExpiresAt())
                .withFirstName(account.getFirstName())
                .withLastName(account.getLastName())
                .withBirthDate(account.getBirthDate())
                .withPhoneNumber(account.getPhoneNumber())
                .withGender(account.getGender())
                .build();

        repository.update(id, accountToUpdate);

        if ((account.getAddressId() != null && currentAccount.getAddressId() != null && !account.getAddressId().equals(currentAccount.getAddressId()))
                || (account.getAddressId() == null && currentAccount.getAddressId() != null))
        {
            addressFacade.deleteAddressById(currentAccount.getAddressId());
        }

        return getById(id);
    }

    private Boolean isAddressExists(final Long addressId)
    {
        return repository.findByAddressId(addressId) != null;
    }
}
