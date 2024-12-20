package com.turi.attraction.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AttractionType
{
    RELAX(1),
    SPORT(2),
    ENTERTAINMENT(3),
    FOOD(4);

    private final int value;

    public static AttractionType fromValue(final int value)
    {
        return Arrays.stream(AttractionType.values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElse(null);
    }

    public static int getValueOrDefault(final AttractionType type)
    {
        return type != null ? type.getValue() : 0;
    }
}
