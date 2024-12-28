package com.turi.attraction.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum AttractionType
{
    UNASSIGNED(0),
    RELAX(1),
    SPORT(2),
    RECREATION(3),
    ENTERTAINMENT(4),
    FOOD(5),
    EVENT(6),
    CHILDREN(7),
    OTHER(8);

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
