package com.turi.reservation.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.reservation.domain.model.Reservation;
import com.turi.reservation.domain.model.ReservationAttraction;
import com.turi.reservation.domain.port.ReservationAttractionService;
import com.turi.reservation.domain.port.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class ReservationFacade
{
    private final ReservationService reservationService;
    private final ReservationAttractionService reservationAttractionService;

    public ResponseEntity<Reservation> checkPayment(final Long reservationId, final Boolean initial)
    {
        if (reservationId == null || initial == null)
        {
            throw new BadRequestParameterException("Parameters reservationId and initial are required.");
        }

        return ReservationResponse.of(reservationService.checkPayment(reservationId, initial));
    }

    public ResponseEntity<String> payForReservation(final Long reservationId,
                                                    final PaymentMethod method,
                                                    final List<ReservationAttraction> reservationAttractions)
    {
        if (reservationId == null || method == null || reservationAttractions == null)
        {
            throw new BadRequestParameterException("Parameters reservationId, method and reservationAttractions are required.");
        }

        return ReservationResponse.of(reservationService.pay(reservationId, method, reservationAttractions));
    }

    public ResponseEntity<String> payForReservationDateExtension(final Long reservationId,
                                                                 final PaymentMethod method,
                                                                 final LocalDate dateTo)
    {
        if (reservationId == null || method == null || dateTo == null)
        {
            throw new BadRequestParameterException("Parameters reservationId, method and dateTo are required.");
        }

        return ReservationResponse.of(reservationService.payForDateExtension(reservationId, method, dateTo));
    }

    public ResponseEntity<String> payForReservationAttractions(final Long reservationId,
                                                               final PaymentMethod method,
                                                               final List<ReservationAttraction> reservationAttractions)
    {
        if (reservationId == null || method == null || reservationAttractions == null)
        {
            throw new BadRequestParameterException("Parameters reservationId, method and reservationAttractions are required.");
        }

        return ReservationResponse.of(reservationService.payForAttractions(reservationId, method, reservationAttractions));
    }
}
