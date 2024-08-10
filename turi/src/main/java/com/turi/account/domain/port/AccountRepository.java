package com.turi.account.domain.port;

import com.turi.account.domain.model.Account;

import java.util.List;

public interface AccountRepository
{
    List<Account> findAll();

    Account findById(final Long id);

    Account findByUserId(final Long userId);

    Account findByAddressId(final Long addressId);

    Account findByPhoneNumber(final String phoneNumber);

    Long insert(final Account account);

    void update(final Long accountId, final Account account);

    void delete(final Long accountId);
}
