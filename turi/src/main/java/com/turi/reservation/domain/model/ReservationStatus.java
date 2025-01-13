package com.turi.reservation.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ReservationStatus
{
    LOCKED(0, "LOCKED"),
    UNPAID(1, "UNPAID"),
    RESERVATION(2, "RESERVATION"),
    REALIZATION(3, "REALIZATION"),
    REALIZED(4, "REALIZED"),
    CANCELED(5, "CANCELED");

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
