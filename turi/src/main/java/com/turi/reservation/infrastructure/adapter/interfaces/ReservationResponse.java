package com.turi.reservation.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.Attraction;
import com.turi.infrastructure.exception.BadRequestResponseException;
import com.turi.reservation.domain.model.Reservation;
import com.turi.reservation.domain.model.ReservationAttraction;
import com.turi.reservation.domain.model.ReservationDto;
import com.turi.stay.domain.model.StayDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    public static ResponseEntity<ReservationDto> of(final ReservationDto reservation)
    {
        if (reservation == null)
        {
            throw new BadRequestResponseException("Reservation with attractions response must not be null.");
        }

        return ResponseEntity.ok(reservation);
    }

    public static ResponseEntity<ReservationAttraction> of(final ReservationAttraction reservationAttraction)
    {
        if (reservationAttraction == null)
        {
            throw new BadRequestResponseException("Reservation attraction response must not be null.");
        }

        return ResponseEntity.ok(reservationAttraction);
    }

    public static ResponseEntity<List<ReservationDto>> ofReservations(final List<ReservationDto> reservations)
    {
        if (reservations == null)
        {
            throw new BadRequestResponseException("Reservations with attractions response must not be null.");
        }

        return ResponseEntity.ok(reservations);
    }

    public static ResponseEntity<List<StayDto>> ofStays(final List<StayDto> stays)
    {
        if (stays == null)
        {
            throw new BadRequestResponseException("Available stays response must not be null.");
        }

        return ResponseEntity.ok(stays);
    }

    public static ResponseEntity<List<Attraction>> ofAttractions(final List<Attraction> attractions)
    {
        if (attractions == null)
        {
            throw new BadRequestResponseException("Available attractions response must not be null.");
        }

        return ResponseEntity.ok(attractions);
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
