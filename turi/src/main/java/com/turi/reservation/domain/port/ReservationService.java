package com.turi.reservation.domain.port;

import com.turi.attraction.domain.model.Attraction;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.reservation.domain.model.*;
import com.turi.stay.domain.model.StayDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService
{
    List<ReservationDto> getAllByAccountId(final Long accountId,
                                           final ReservationStatus[] statuses);

    List<ReservationDto> getAllByTouristicPlaceId(final Long touristicPlaceId,
                                                  final ReservationStatus[] statuses);

    ReservationDto getWithAttractionsById(final Long id);

    List<StayDto> getAllTouristicPlaceStaysAvailableInDate(final Long touristicPlaceId,
                                                           final LocalDate dateFrom,
                                                           final LocalDate dateTo);

    List<Attraction> getAllTouristicPlaceAttractionsAvailableInDate(final Long touristicPlaceId,
                                                                    final LocalDate dateFrom,
                                                                    final LocalDate dateTo);

    ReservationDto checkPayment(final Long id,
                                final ReservationMode[] modes);

    Reservation create(final Long stayId,
                       final Long accountId,
                       final LocalDate dateFrom,
                       final LocalDate dateTo);

    ReservationAttraction createAttraction(final Long id,
                                           final Long attractionId,
                                           final LocalDate dateFrom,
                                           final LocalDate dateTo,
                                           final LocalTime hourFrom,
                                           final LocalTime hourTo);

    String pay(final Long id,
               final PaymentMethod method,
               final ReservationMode mode,
               final LocalDate dateTo,
               final List<ReservationAttraction> reservationAttractions);

    ReservationDto makePayOnSite(final Long id,
                                 final ReservationMode mode,
                                 final LocalDate dateTo,
                                 final List<ReservationAttraction> reservationAttractions);

    ReservationDto payOnSite(final Long id,
                             final ReservationMode mode,
                             final List<ReservationAttraction> reservationAttractions);

    void createAllReservationsReminder();

    ReservationDto updateDetails(final Long id,
                                 final LocalTime checkInTime,
                                 final String request);

    ReservationDto updateOpinion(final Long id,
                                 final Double rating,
                                 final String opinion);

    ReservationDto updateDateTo(final Long id,
                                final LocalDate dateTo);

    void updateAllReservationsStatuses();

    void updateAllReservationsAttractionsStatuses();

    void cancel(final Long id);

    void cancelAttraction(final Long reservationAttractionId);

    void deleteAllExpiredLockedReservations();

    void deleteAllExpiredLockedReservationsAttractions();
}
