package com.turi.account.domain.port;

import com.turi.account.domain.model.Account;

public interface AccountService
{
    Account getById(final Long id);

    Boolean isAddressExists(final String country,
                            final String city,
                            final String zipCode,
                            final String street,
                            final String buildingNumber,
                            final Integer apartmentNumber);

    Account getByLogin(final String login);

    Boolean isLoginExists(final String login);

    Account getByEmail(final String email);

    Boolean isEmailExists(final String email);

    Boolean isPhoneNumberExists(final String phoneNumber);

    Account createAccount(final Account account);

    Account updateOwnerDetails(final Long accountId, final Account account);

    Account resetPassword(final Long accountId, final String password);
}
