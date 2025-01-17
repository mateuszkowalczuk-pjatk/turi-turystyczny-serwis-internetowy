package com.turi.reservation.domain.port;

import com.turi.reservation.domain.model.ReservationAttraction;

import java.util.List;

public interface ReservationAttractionRepository
{
    List<ReservationAttraction> findAllByReservationId(final Long reservationId);

    ReservationAttraction findById(final Long reservationAttractionId);

    Long insert(final ReservationAttraction reservationAttraction);

    void update(final Long id, final ReservationAttraction reservationAttraction);

    void delete(final Long id);
}
