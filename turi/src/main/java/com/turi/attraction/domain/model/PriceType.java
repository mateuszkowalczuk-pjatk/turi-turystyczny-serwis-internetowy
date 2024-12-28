package com.turi.attraction.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PriceType
{
    UNASSIGNED(0),
    HOUR(1),
    PERSON(2),
    ITEM(3);

    private final int value;

    public static PriceType fromValue(final int value)
    {
        return Arrays.stream(PriceType.values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElse(null);
    }

    public static int getValueOrDefault(final PriceType type)
    {
        return type != null ? type.getValue() : 0;
    }
}
