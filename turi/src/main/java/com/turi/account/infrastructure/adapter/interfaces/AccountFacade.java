package com.turi.account.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.account.domain.port.AccountService;
import com.turi.infrastructure.common.ObjectId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountFacade
{
    private final AccountService service;

    public Account getByUserId(final Long userId)
    {
        return AccountResponse.of(service.getByUserId(userId));
    }

    public Boolean isAddressExists(final String country,
                                   final String city,
                                   final String zipCode,
                                   final String street,
                                   final String buildingNumber,
                                   final Integer apartmentNumber)
    {
        return AccountResponse.of(service.isAddressExists(country,
                city,
                zipCode,
                street,
                buildingNumber,
                apartmentNumber));
    }

    public Boolean isPhoneNumberExists(final String phoneNumber)
    {
        return AccountResponse.of(service.isPhoneNumberExists(phoneNumber));
    }

    public Account createAccount(final Account account)
    {
        return AccountResponse.of(service.createAccount(account));
    }

    public Account updateAccount(final String accountId, final Account account)
    {
        final var id = ObjectId.of(accountId).getValue();

        return AccountResponse.of(service.updateAccount(id, account));
    }
}
