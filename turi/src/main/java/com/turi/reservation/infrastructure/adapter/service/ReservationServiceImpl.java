package com.turi.reservation.infrastructure.adapter.service;

import com.turi.attraction.infrastructure.adapter.interfaces.AttractionFacade;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.infrastructure.adapter.interfaces.PaymentFacade;
import com.turi.reservation.domain.exception.ReservationCompletedException;
import com.turi.reservation.domain.exception.ReservationUnpaidException;
import com.turi.reservation.domain.model.Reservation;
import com.turi.reservation.domain.model.ReservationAttraction;
import com.turi.reservation.domain.model.ReservationStatus;
import com.turi.reservation.domain.port.ReservationRepository;
import com.turi.reservation.domain.port.ReservationService;
import com.turi.stay.infrastructure.adapter.interfaces.StayFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService
{
    private final StayFacade stayFacade;
    private final PaymentFacade paymentFacade;
    private final ReservationRepository repository;
    private final AttractionFacade attractionFacade;

    @Override
    public Reservation getById(final Long id)
    {
        return repository.findById(id);
    }

    @Override
    public Reservation checkPayment(final Long reservationId, final Boolean initial)
    {
        final var reservation = getById(reservationId);

        if (!paymentFacade.isPaymentForReservationSucceeded(reservationId))
        {
            throw new ReservationUnpaidException(reservationId);
        }

        if (initial)
        {
            updateStatus(reservation, ReservationStatus.RESERVATION);
        }

        return getById(reservationId);
    }

    @Override
    public Boolean isStayLocked(Long stayId, LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }

    @Override
    public Boolean isAttractionLocked(Long stayId, LocalDate dateFrom, LocalDate dateTo, LocalTime hourFrom, LocalTime hourTo) {
        return null;
    }

    @Override
    public Reservation create(final Long stayId, final Long accountId, final LocalDate dateFrom, final LocalDate dateTo)
    {
        final var reservation = Reservation.builder()
                .withStayId(stayId)
                .withAccountId(accountId)
                .withDateFrom(dateFrom)
                .withDateTo(dateTo)
                .withModifyDate(LocalDateTime.now())
                .withStatus(ReservationStatus.LOCKED)
                .build();

        final var reservationId =  repository.insert(reservation);

        return getById(reservationId);
    }

    @Override
    public ReservationAttraction createAttraction() {
        return null;
    }

    @Override
    public String pay(final Long reservationId,
                      final PaymentMethod method,
                      final List<ReservationAttraction> reservationAttractions)
    {
        final var reservation = getById(reservationId);

        if (reservation.getStatus().equals(ReservationStatus.REALIZED) || reservation.getStatus().equals(ReservationStatus.CANCELED))
        {
            throw new ReservationCompletedException(reservation.getReservationId());
        }
        
        final var price = calculatePrice(reservation, reservationAttractions, null);

        updateStatus(reservation, ReservationStatus.UNPAID);

        return paymentFacade.payForReservation(reservationId, price, method, reservationAttractions);
    }

    @Override
    public String payForDateExtension(final Long reservationId,
                                      final PaymentMethod method,
                                      final LocalDate dateTo)
    {
        final var reservation = getById(reservationId);

        if (reservation.getStatus().equals(ReservationStatus.REALIZED) || reservation.getStatus().equals(ReservationStatus.CANCELED))
        {
            throw new ReservationCompletedException(reservation.getReservationId());
        }

        final var price = calculatePrice(null, null, dateTo);

        return paymentFacade.payForReservation(reservationId, price, method, Collections.emptyList());
    }

    @Override
    public String payForAttractions(final Long reservationId,
                                    final PaymentMethod method,
                                    final List<ReservationAttraction> reservationAttractions)
    {
        final var reservation = getById(reservationId);

        if (reservation.getStatus().equals(ReservationStatus.REALIZED) || reservation.getStatus().equals(ReservationStatus.CANCELED))
        {
            throw new ReservationCompletedException(reservation.getReservationId());
        }

        final var price = calculatePrice(null, reservationAttractions, null);

        return paymentFacade.payForReservation(reservationId, price, method, reservationAttractions);
    }

    private Double calculatePrice(final Reservation reservation,
                                  final List<ReservationAttraction> reservationAttractions,
                                  final LocalDate dateTo)
    {
        var price = 0.0;

        if (reservation != null)
        {
            // ToDo - logika obliczania ceny pobytu na podstawie ceny za noc
        }

        if (reservationAttractions != null)
        {
            // ToDo - logika obliczania ceny za dodane atrakcje
        }

        if (dateTo != null)
        {
            // ToDo - logika obliczania ceny pobytu na podstawie różnicy pomiędzy aktualną datą pobytu do i nową datą pobytu do
        }

        return price;
    }

    private void updateStatus(final Reservation reservation, final ReservationStatus status)
    {
        reservation.setStatus(status);

        repository.update(reservation.getReservationId(), reservation);
    }

    @Override
    public void createAllReservationsReminder() {

    }

    @Override
    public Reservation updateDetails(Long reservationId, LocalTime checkInTime, String request) {
        return null;
    }

    @Override
    public Reservation updateOpinion(Long reservationId, Double rating, String opinion) {
        return null;
    }

    @Override
    public void updateAllReservationsStatuses() {

    }

    @Override
    public void updateAllReservationsAttractionsStatuses() {

    }

    @Override
    public void cancel(Long reservationId) {

    }

    @Override
    public void cancelAttraction(Long reservationId) {

    }

    @Override
    public void deleteAllExpiredLockedReservations() {

    }

    @Override
    public void deleteAllExpiredLockedReservationsAttractions() {

    }
}
