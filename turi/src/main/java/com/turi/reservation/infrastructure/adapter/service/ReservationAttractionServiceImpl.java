package com.turi.reservation.infrastructure.adapter.service;

import com.turi.attraction.infrastructure.adapter.interfaces.AttractionFacade;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.reservation.domain.model.ReservationAttraction;
import com.turi.reservation.domain.model.ReservationStatus;
import com.turi.reservation.domain.port.ReservationAttractionRepository;
import com.turi.reservation.domain.port.ReservationAttractionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationAttractionServiceImpl implements ReservationAttractionService
{
    private final AttractionFacade attractionFacade;
    private final ReservationAttractionRepository repository;

    private List<ReservationAttraction> getAll()
    {
        return repository.findAll();
    }

    @Override
    public List<ReservationAttraction> getAllByReservationId(final Long reservationId)
    {
        return repository.findAllByReservationId(reservationId);
    }

    @Override
    public List<ReservationAttraction> getAllByAttractionId(final Long attractionId)
    {
        return repository.findAllByAttractionId(attractionId);
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
                .filter(reservationAttraction -> reservationAttraction.getStatus().equals(ReservationStatus.LOCKED)
                        || reservationAttraction.getStatus().equals(ReservationStatus.UNPAID)
                        || reservationAttraction.getStatus().equals(ReservationStatus.PAY_ON_SITE))
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
    public void updateAllReservationsAttractionsStatuses()
    {
        getAll().forEach(reservationAttraction -> {
            final var attraction = attractionFacade.getAttractionById(String.valueOf(reservationAttraction.getAttractionId())).getBody();

            if (reservationAttraction.getStatus().equals(ReservationStatus.UNPAID)
                    && attraction != null && attraction.getCancelReservationDays() != null
                    && reservationAttraction.getDateFrom().plusDays(attraction.getCancelReservationDays()).isBefore(LocalDate.now()))
            {
                updateStatus(reservationAttraction, ReservationStatus.CANCELED);
            }
            else if (reservationAttraction.getStatus().equals(ReservationStatus.RESERVATION)
                    && reservationAttraction.getDateFrom().equals(LocalDate.now())
                    && reservationAttraction.getHourFrom().isAfter(LocalTime.now()))
            {
                updateStatus(reservationAttraction, ReservationStatus.REALIZATION);
            }
            else if (reservationAttraction.getStatus().equals(ReservationStatus.REALIZATION)
                    && reservationAttraction.getDateTo().equals(LocalDate.now())
                    && reservationAttraction.getHourTo().isAfter(LocalTime.now()))
            {
                updateStatus(reservationAttraction, ReservationStatus.REALIZED);
            }
        });
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

    @Override
    public void delete(final Long id)
    {
        final var reservationAttraction = getById(id);

        if (!reservationAttraction.getStatus().equals(ReservationStatus.LOCKED))
        {
            throw new BadRequestParameterException("Reservation attraction must not locked to be removed.");
        }

        repository.delete(reservationAttraction.getReservationAttractionId());
    }

    @Override
    public void deleteAllExpiredLockedReservationsAttractions()
    {
        getAll().stream()
                .filter(reservationAttraction -> (reservationAttraction.getStatus().equals(ReservationStatus.LOCKED) || reservationAttraction.getStatus().equals(ReservationStatus.UNPAID)) && reservationAttraction.getModifyDate().plusMinutes(15).isBefore(LocalDateTime.now()))
                .forEach(reservationAttraction -> cancel(reservationAttraction.getReservationAttractionId()));
    }
}
