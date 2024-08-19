package com.turi.account.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountResponse
{
    public static Account of(final Account account)
    {
        Assert.notNull(account, "Account must not be null.");

        return account;
    }

    public static Boolean of(final Boolean result)
    {
        Assert.notNull(result, "Check result must not be null.");

        return result;
    }
}
