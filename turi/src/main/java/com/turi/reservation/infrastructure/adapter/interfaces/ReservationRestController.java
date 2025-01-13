package com.turi.reservation.infrastructure.adapter.interfaces;

import com.turi.payment.domain.model.PaymentMethod;
import com.turi.reservation.domain.model.Reservation;
import com.turi.reservation.domain.model.ReservationAttraction;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/reservation", produces = "application/json")
public class ReservationRestController
{
    private final ReservationFacade facade;

    @GetMapping("/checkPayment/{reservationId}")
    public ResponseEntity<Reservation> checkPayment(@PathVariable final Long reservationId,
                                                    @RequestParam final Boolean initial)
    {
        return facade.checkPayment(reservationId, initial);
    }

    @PostMapping("/pay/{reservationId}")
    public ResponseEntity<String> payForReservation(@PathVariable final Long reservationId,
                                                    @RequestParam final PaymentMethod method,
                                                    @RequestBody final List<ReservationAttraction> reservationAttractions)
    {
        return facade.payForReservation(reservationId, method, reservationAttractions);
    }

    @PostMapping("/payForDateExtension/{reservationId}")
    public ResponseEntity<String> payForReservationDateExtension(@PathVariable final Long reservationId,
                                                                 @RequestParam final PaymentMethod method,
                                                                 @RequestParam final LocalDate dateTo)
    {
        return facade.payForReservationDateExtension(reservationId, method, dateTo);
    }

    @PostMapping("/payForAttractions/{reservationId}")
    public ResponseEntity<String> payForReservationAttractions(@PathVariable final Long reservationId,
                                                               @RequestParam final PaymentMethod method,
                                                               @RequestBody final List<ReservationAttraction> reservationAttractions)
    {
        return facade.payForReservationAttractions(reservationId, method, reservationAttractions);
    }
}
