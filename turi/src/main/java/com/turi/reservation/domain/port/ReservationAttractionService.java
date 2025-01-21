package com.turi.reservation.domain.port;

import com.turi.reservation.domain.model.ReservationAttraction;
import com.turi.reservation.domain.model.ReservationStatus;

import java.util.List;

public interface ReservationAttractionService
{
    List<ReservationAttraction> getAllByReservationId(final Long reservationId);

    List<ReservationAttraction> getAllByAttractionId(final Long attractionId);

    ReservationAttraction getById(final Long id);

    ReservationAttraction create(final ReservationAttraction reservationAttraction);

    void updateAllStatusesByReservationId(final Long reservationId,
                                          final ReservationStatus status);

    void updatePrice(final Long reservationAttractionId,
                     final Double price);

    void updateAllReservationsAttractionsStatuses();

    void cancel(final Long id);

    void delete(final Long id);

    void deleteAllExpiredLockedReservationsAttractions();
}
