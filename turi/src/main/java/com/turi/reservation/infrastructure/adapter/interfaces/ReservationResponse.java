package com.turi.reservation.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.reservation.domain.model.Reservation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReservationResponse
{
    public static ResponseEntity<Reservation> of(final Reservation reservation)
    {
        if (reservation == null)
        {
            throw new BadRequestResponseException("Reservation response must not be null.");
        }

        return ResponseEntity.ok(reservation);
    }

    public static ResponseEntity<String> of(final String value)
    {
        if (value == null)
        {
            throw new BadRequestResponseException("Reservation value response must not be null.");
        }

        return ResponseEntity.ok(value);
    }
}
