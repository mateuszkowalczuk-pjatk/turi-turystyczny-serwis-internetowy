package com.turi.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AccountType
{
    NORMAL(1),
    PREMIUM(2);

    private final int value;

    public static AccountType fromValue(final int value)
    {
        return Arrays.stream(AccountType.values())
                .filter(accountType -> accountType.getValue() == value)
                .findFirst()
                .orElse(null);
    }
}
