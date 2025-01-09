package com.turi.reservation.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ReservationStatus
{
    UNPAID(0, "UNPAID"),
    RESERVATION(1, "RESERVATION"),
    REALIZATION(2, "REALIZATION"),
    REALIZED(3, "REALIZED"),
    CANCELED(4, "CANCELED");

    private final int value;
    private final String name;

    public static ReservationStatus fromValue(final int value)
    {
        return Arrays.stream(ReservationStatus.values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElse(null);
    }
}
