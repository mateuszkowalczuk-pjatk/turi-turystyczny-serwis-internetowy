package com.turi.reservation.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationMode
{
    INITIAL("INITIAL"),
    DATE_EXTENSION("DATE_EXTENSION"),
    ATTRACTIONS("ATTRACTIONS");

    private final String name;
}
