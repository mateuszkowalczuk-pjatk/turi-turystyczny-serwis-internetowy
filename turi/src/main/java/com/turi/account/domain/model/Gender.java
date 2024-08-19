package com.turi.account.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Gender
{
    MALE(1),
    FEMALE(2);

    private final int value;

    public static Gender fromValue(final int value)
    {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.getValue() == value)
                .findFirst()
                .orElse(null);
    }

    public static int getValueOrDefault(final Gender gender)
    {
        return gender != null ? gender.getValue() : 0;
    }
}
