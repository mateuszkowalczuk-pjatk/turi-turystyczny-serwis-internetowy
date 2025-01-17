package com.turi.reservation.infrastructure.adapter.service;

import com.turi.reservation.domain.model.ReservationAttraction;
import com.turi.reservation.domain.model.ReservationStatus;
import com.turi.reservation.domain.port.ReservationAttractionRepository;
import com.turi.reservation.domain.port.ReservationAttractionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationAttractionServiceImpl implements ReservationAttractionService
{
    private final ReservationAttractionRepository repository;

    @Override
    public List<ReservationAttraction> getAllByReservationId(final Long reservationId)
    {
        return repository.findAllByReservationId(reservationId);
    }

    @Override
    public ReservationAttraction getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public ReservationAttraction create(final ReservationAttraction reservationAttraction)
    {
        final var reservationAttractionId = repository.insert(reservationAttraction);

        return getById(reservationAttractionId);
    }

    @Override
    public void updateAllStatusesByReservationId(final Long reservationId,
                                                 final ReservationStatus status)
    {
        getAllByReservationId(reservationId).stream()
                .filter(reservationAttraction -> reservationAttraction.getStatus().equals(ReservationStatus.LOCKED) || reservationAttraction.getStatus().equals(ReservationStatus.UNPAID))
                .forEach(reservationAttraction -> updateStatus(reservationAttraction, status));
    }

    @Override
    public void updatePrice(final Long reservationAttractionId,
                            final Double price)
    {
        final var reservationAttraction = getById(reservationAttractionId);

        reservationAttraction.setPrice(price);

        repository.update(reservationAttractionId, reservationAttraction);
    }

    @Override
    public void cancel(final Long id)
    {
        final var reservationAttraction = getById(id);

        updateStatus(reservationAttraction, ReservationStatus.CANCELED);
    }

    private void updateStatus(final ReservationAttraction reservationAttraction, final ReservationStatus status)
    {
        reservationAttraction.setStatus(status);
        reservationAttraction.setModifyDate(LocalDateTime.now());

        repository.update(reservationAttraction.getReservationAttractionId(), reservationAttraction);
    }
}
