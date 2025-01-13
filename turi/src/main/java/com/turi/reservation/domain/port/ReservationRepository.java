package com.turi.reservation.domain.port;

import com.turi.reservation.domain.model.Reservation;

public interface ReservationRepository
{
    Reservation findById(final Long id);

    Long insert(final Reservation reservation);

    void update(final Long id, final Reservation reservation);

    void delete(final Long id);
}
