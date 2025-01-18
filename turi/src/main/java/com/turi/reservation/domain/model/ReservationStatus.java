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
    PAY_ON_SITE(2, "PAY_ON_SITE"),
    RESERVATION(3, "RESERVATION"),
    RESERVATION_UNPAID_DATE_EXTENSION(4, "RESERVATION_UNPAID_DATE_EXTENSION"),
    REALIZATION(5, "REALIZATION"),
    REALIZATION_PAY_ON_SITE_DATE_EXTENSION(6, "REALIZATION_PAY_ON_SITE_DATE_EXTENSION"),
    REALIZATION_UNPAID_DATE_EXTENSION(7, "REALIZATION_UNPAID_DATE_EXTENSION"),
    REALIZED(8, "REALIZED"),
    CANCELED(9, "CANCELED");

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
