package com.turi.reservation.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.Attraction;
import com.turi.infrastructure.common.ContextHandler;
import com.turi.infrastructure.common.ObjectId;
import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.reservation.domain.model.*;
import com.turi.reservation.domain.port.ReservationService;
import com.turi.stay.domain.model.StayDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@AllArgsConstructor
public class ReservationFacade
{
    private final ReservationService service;

    public ResponseEntity<List<ReservationDto>> getAllReservationsByAccountId(final ReservationStatus[] statuses)
    {
        if (statuses == null)
        {
            throw new BadRequestParameterException("Parameter statuses is required.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        return ReservationResponse.ofReservations(service.getAllByAccountId(accountId, statuses));
    }

    public ResponseEntity<List<ReservationDto>> getAllReservationsByTouristicPlaceId(final String touristicPlaceId,
                                                                                     final ReservationStatus[] statuses)
    {
        if (touristicPlaceId == null || statuses == null)
        {
            throw new BadRequestParameterException("Parameters touristicPlaceId and statuses are required.");
        }

        return ReservationResponse.ofReservations(service.getAllByTouristicPlaceId(ObjectId.of(touristicPlaceId).getValue(), statuses));
    }

    public ResponseEntity<ReservationDto> getReservationWithAttractionsById(final String id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter ID is required.");
        }

        return ReservationResponse.of(service.getWithAttractionsById(ObjectId.of(id).getValue()));
    }

    public ResponseEntity<List<StayDto>> getAllTouristicPlaceStaysAvailableInDate(final String touristicPlaceId,
                                                                                  final LocalDate dateFrom,
                                                                                  final LocalDate dateTo)
    {
        if (touristicPlaceId == null || dateFrom == null || dateTo == null)
        {
            throw new BadRequestParameterException("Parameters touristicPlaceId, dateFrom and dateTo are required.");
        }

        return ReservationResponse.ofStays(service.getAllTouristicPlaceStaysAvailableInDate(ObjectId.of(touristicPlaceId).getValue(), dateFrom, dateTo));
    }

    public ResponseEntity<List<Attraction>> getAllTouristicPlaceAttractionsAvailableInDate(final String touristicPlaceId,
                                                                                           final LocalDate dateFrom,
                                                                                           final LocalDate dateTo)
    {
        if (touristicPlaceId == null || dateFrom == null || dateTo == null)
        {
            throw new BadRequestParameterException("Parameters touristicPlaceId, dateFrom and dateTo are required.");
        }

        return ReservationResponse.ofAttractions(service.getAllTouristicPlaceAttractionsAvailableInDate(ObjectId.of(touristicPlaceId).getValue(), dateFrom, dateTo));
    }

    public ResponseEntity<ReservationDto> checkPayment(final String id,
                                                       final ReservationMode[] modes)
    {
        if (id == null || modes == null)
        {
            throw new BadRequestParameterException("Parameters ID and modes are required.");
        }

        return ReservationResponse.of(service.checkPayment(ObjectId.of(id).getValue(), modes));
    }

    public ResponseEntity<Reservation> createReservation(final String stayId,
                                                         final LocalDate dateFrom,
                                                         final LocalDate dateTo)
    {
        if (stayId == null || dateFrom == null || dateTo == null)
        {
            throw new BadRequestParameterException("Parameters stayId, dateFrom and dateTo are required.");
        }

        final var accountId = ContextHandler.getIdFromContext();

        return ReservationResponse.of(service.create(ObjectId.of(stayId).getValue(), accountId, dateFrom, dateTo));
    }

    public ResponseEntity<ReservationAttraction> createReservationAttraction(final String id,
                                                                             final String attractionId,
                                                                             final LocalDate dateFrom,
                                                                             final LocalDate dateTo,
                                                                             final LocalTime hourFrom,
                                                                             final LocalTime hourTo)
    {
        if (id == null || attractionId == null || dateFrom == null || dateTo == null || hourFrom == null || hourTo == null)
        {
            throw new BadRequestParameterException("Parameters ID, attractionId, dateFrom, dateTo, hourFrom and hourTo are required.");
        }

        return ReservationResponse.of(service.createAttraction(ObjectId.of(id).getValue(), ObjectId.of(attractionId).getValue(), dateFrom, dateTo, hourFrom, hourTo));
    }

    public ResponseEntity<String> payForReservation(final String id,
                                                    final PaymentMethod method,
                                                    final ReservationMode mode,
                                                    final LocalDate dateTo,
                                                    final List<ReservationAttraction> reservationAttractions)
    {
        if (id == null || method == null || mode == null)
        {
            throw new BadRequestParameterException("Parameters ID, method and mode are required.");
        }

        return ReservationResponse.of(service.pay(ObjectId.of(id).getValue(), method, mode, dateTo, reservationAttractions));
    }

    public ResponseEntity<ReservationDto> makePayForReservationOnSite(final String id,
                                                                      final ReservationMode mode,
                                                                      final List<ReservationAttraction> reservationAttractions)
    {
        if (id == null || mode == null)
        {
            throw new BadRequestParameterException("Parameters ID and mode are required.");
        }

        return ReservationResponse.of(service.makePayOnSite(ObjectId.of(id).getValue(), mode, reservationAttractions));
    }

    public ResponseEntity<ReservationDto> payForReservationOnSite(final String id,
                                                                  final ReservationMode mode,
                                                                  final List<ReservationAttraction> reservationAttractions)
    {
        if (id == null || mode == null)
        {
            throw new BadRequestParameterException("Parameters ID and mode are required.");
        }

        return ReservationResponse.of(service.payOnSite(ObjectId.of(id).getValue(), mode, reservationAttractions));
    }

    public ResponseEntity<ReservationDto> updateReservationDetails(final String id,
                                                                   final LocalTime checkInTime,
                                                                   final String request)
    {
        if (id == null || checkInTime == null)
        {
            throw new BadRequestParameterException("Parameters ID and checkInTime are required.");
        }

        return ReservationResponse.of(service.updateDetails(ObjectId.of(id).getValue(), checkInTime, request));
    }

    public ResponseEntity<ReservationDto> updateReservationOpinion(final String id,
                                                                   final Double rating,
                                                                   final String opinion)
    {
        if (id == null || rating == null || opinion == null)
        {
            throw new BadRequestParameterException("Parameters ID, rating and opinion are required.");
        }

        return ReservationResponse.of(service.updateOpinion(ObjectId.of(id).getValue(), rating, opinion));
    }

    public ResponseEntity<ReservationDto> updateReservationDateTo(final String id, final LocalDate dateTo)
    {
        if (id == null || dateTo == null)
        {
            throw new BadRequestParameterException("Parameters ID and dateTo are required.");
        }

        return ReservationResponse.of(service.updateDateTo(ObjectId.of(id).getValue(), dateTo));
    }

    public ResponseEntity<?> cancelReservation(final String id)
    {
        if (id == null)
        {
            throw new BadRequestParameterException("Parameter ID is required.");
        }

        service.cancel(ObjectId.of(id).getValue());

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> cancelReservationAttraction(final String reservationAttractionId)
    {
        if (reservationAttractionId == null)
        {
            throw new BadRequestParameterException("Parameter reservationAttractionId is required.");
        }

        service.cancelAttraction(ObjectId.of(reservationAttractionId).getValue());

        return ResponseEntity.ok().build();
    }
}
