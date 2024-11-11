package com.turi.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AccountType
{
    INACTIVE(0, "INACTIVE"),
    NORMAL(1, "NORMAL"),
    PREMIUM(2, "PREMIUM");

    private final int value;
    private final String name;

    public static AccountType fromValue(final int value)
    {
        return Arrays.stream(AccountType.values())
                .filter(accountType -> accountType.getValue() == value)
                .findFirst()
                .orElse(null);
    }
}
