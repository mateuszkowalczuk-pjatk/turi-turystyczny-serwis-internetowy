package com.turi.reservation.domain.port;

import com.turi.reservation.domain.model.Reservation;

import java.util.List;

public interface ReservationRepository
{
    List<Reservation> findAllByStayId(final Long stayId);

    List<Reservation> findAllByAccountId(final Long accountId);

    Reservation findById(final Long id);

    Long insert(final Reservation reservation);

    void update(final Long id, final Reservation reservation);

    void delete(final Long id);
}
