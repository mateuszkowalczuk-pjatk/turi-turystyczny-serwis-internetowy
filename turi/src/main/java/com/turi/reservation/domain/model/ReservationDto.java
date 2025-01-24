package com.turi.reservation.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public final class ReservationDto
{
    private Reservation reservation;
    private List<ReservationAttraction> attractions;
}
