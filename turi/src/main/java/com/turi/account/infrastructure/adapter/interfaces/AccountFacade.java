package com.turi.account.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.port.AccountService;
import com.turi.infrastructure.common.ContextHandler;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.infrastructure.exception.BadRequestResponseException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountFacade
{
    private final AccountService service;

    public ResponseEntity<Account> getAccountById()
    {
        final var accountId = ContextHandler.getIdFromContext();

        return AccountResponse.of(service.getById(accountId));
    }

    public Account getAccountByUserId(final Long id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter ID must not be null.");
        }

        final var account = service.getByUserId(id);

        if (account == null)
        {
            throw new BadRequestResponseException("Account response must not be null.");
        }

        return account;
    }

    public ResponseEntity<Boolean> isAccountAddressExists(final String country,
                                                          final String city,
                                                          final String zipCode,
                                                          final String street,
                                                          final String buildingNumber,
                                                          final String apartmentNumber)
    {
        if (country == null || city == null || zipCode == null || street == null || buildingNumber == null)
        {
            throw new BadRequestParameterException("Parameters country, city, zipCode, street and buildingNumber must not be null.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        return AccountResponse.of(service.isAddressExists(accountId,
                country,
                city,
                zipCode,
                street,
                buildingNumber,
                apartmentNumber != null ? Integer.valueOf(apartmentNumber) : null));
    }

    public ResponseEntity<Boolean> isAccountPhoneNumberExists(final String phoneNumber)
    {
        if (phoneNumber == null)
        {
            throw new BadRequestParameterException("Parameter phoneNumber must not be null.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        return AccountResponse.of(service.isPhoneNumberExists(accountId, phoneNumber));
    }

    public ResponseEntity<?> activateAccount(final String code, final HttpServletResponse response)
    {
        if (code == null)
        {
            throw new BadRequestParameterException("Parameter code must not be null.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        service.activate(accountId, Integer.valueOf(code));

        return AccountResponse.of(response);
    }

    public void sendAccountActivateCode(final Long accountId)
    {
        service.sendActivateCode(accountId);
    }

    public Account createAccount(final Account account)
    {
        if (account == null)
        {
            throw new BadRequestParameterException("Parameter account must not be null.");
        }

        return service.create(account);
    }

    public ResponseEntity<Account> updateAccount(final Account account)
    {
        if (account == null)
        {
            throw new BadRequestParameterException("Parameter account must not be null.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        return AccountResponse.of(service.update(accountId, account));
    }

    public void updateAccountTypeToPremium()
    {
        final var accountId = ContextHandler.getIdFromContext();

        service.updateAccountTypeToPremium(accountId);
    }

    public void updateAccountTypeToNormal()
    {
        final var accountId = ContextHandler.getIdFromContext();

        service.updateAccountTypeToNormal(accountId);
    }
}
