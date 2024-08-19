package com.turi.account.domain.port;

import com.turi.account.domain.model.Account;

public interface AccountService
{
    Account getById(final Long id);

    Account getByUserId(final Long userId);

    Boolean isAddressExists(final String country,
                            final String city,
                            final String zipCode,
                            final String street,
                            final String buildingNumber,
                            final Integer apartmentNumber);

    Boolean isPhoneNumberExists(final String phoneNumber);

    Account createAccount(final Account account);

    Account updateAccount(final Long accountId, final Account account);
}
