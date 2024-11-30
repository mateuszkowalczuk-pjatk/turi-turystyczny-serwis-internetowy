package com.turi.premium.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PremiumStatus
{
    UNPAID(0, "UNPAID"),
    ACTIVE(1, "ACTIVE"),
    EXPIRED(2, "EXPIRED");

    private final int value;
    private final String name;

    public static PremiumStatus fromValue(final int value)
    {
        return Arrays.stream(PremiumStatus.values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElse(null);
    }

    public static int getValueOrDefault(final PremiumStatus status)
    {
        return status != null ? status.getValue() : 0;
    }
}
