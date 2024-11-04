package com.turi.account.infrastructure.adapter.interfaces;

import com.turi.account.domain.model.Account;
import com.turi.infrastructure.exception.BadRequestResponseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AccountResponse
{
    public static ResponseEntity<Account> of(final Account account)
    {
        if (account == null)
        {
            throw new BadRequestResponseException("Account response must not be null.");
        }

        return ResponseEntity.ok(account);
    }

    public static ResponseEntity<Boolean> of(final Boolean result)
    {
        if (result == null)
        {
            throw new BadRequestResponseException("Check result response must not be null.");
        }

        return ResponseEntity.ok(result);
    }
}
