package com.turi.reservation.domain.port;

import com.turi.payment.domain.model.PaymentMethod;
import com.turi.reservation.domain.model.Reservation;
import com.turi.reservation.domain.model.ReservationAttraction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService
{
    //ToDo - GET

    Reservation getById(final Long id);

    Reservation checkPayment(final Long reservationId, final Boolean initial);

    Boolean isStayLocked(final Long stayId, final LocalDate dateFrom, final LocalDate dateTo);

    Boolean isAttractionLocked(final Long stayId,
                               final LocalDate dateFrom,
                               final LocalDate dateTo,
                               final LocalTime hourFrom,
                               final LocalTime hourTo);

    Reservation create(final Long stayId, final Long accountId, final LocalDate dateFrom, final LocalDate dateTo);

    ReservationAttraction createAttraction();

    String pay(final Long reservationId,
               final PaymentMethod method,
               final List<ReservationAttraction> reservationAttractions);

    String payForDateExtension(final Long reservationId,
                               final PaymentMethod method,
                               final LocalDate dateTo);

    String payForAttractions(final Long reservationId,
                             final PaymentMethod method,
                             final List<ReservationAttraction> reservationAttractions);

    void createAllReservationsReminder();

    Reservation updateDetails(final Long reservationId, final LocalTime checkInTime, final String request);

    Reservation updateOpinion(final Long reservationId, final Double rating, final String opinion);

    void updateAllReservationsStatuses();

    void updateAllReservationsAttractionsStatuses();

    void cancel(final Long reservationId);

    void cancelAttraction(final Long reservationId);

    void deleteAllExpiredLockedReservations();

    void deleteAllExpiredLockedReservationsAttractions();

    // ToDo - locked na update DatyDo

    // ToDo - locked na update rezerwacji
}
