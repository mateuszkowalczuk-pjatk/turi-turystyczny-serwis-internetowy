package com.turi.touristicplace.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TouristicPlaceType
{
    UNASSIGNED(0),
    GUESTHOUSE(1),
    APARTMENT(2),
    COTTAGES(3),
    HOTEL(4),
    BB(5),
    HOSTEL(6);

    private final int value;

    public static TouristicPlaceType fromValue(final int value)
    {
        return Arrays.stream(TouristicPlaceType.values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElse(null);
    }

    public static int getValueOrDefault(final TouristicPlaceType type)
    {
        return type != null ? type.getValue() : 0;
    }
}
