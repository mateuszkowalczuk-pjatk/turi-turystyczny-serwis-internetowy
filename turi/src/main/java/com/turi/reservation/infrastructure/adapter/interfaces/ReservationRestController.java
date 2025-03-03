package com.turi.reservation.infrastructure.adapter.interfaces;

import com.turi.attraction.domain.model.Attraction;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.reservation.domain.model.*;
import com.turi.stay.domain.model.StayDto;
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

    @GetMapping("/getAllAttractionsByReservationId/{reservationId}")
    public ResponseEntity<List<ReservationAttraction>> getAllReservationAttractionsByReservationId(@PathVariable final String reservationId)
    {
        return facade.getAllReservationAttractionsByReservationId(reservationId);
    }

    @GetMapping("/getWithAttractionsById/{id}")
    public ResponseEntity<ReservationDto> getReservationWithAttractionsById(@PathVariable final String id)
    {
        return facade.getReservationWithAttractionsById(id);
    }

    @GetMapping("/getPrice/{id}")
    public ResponseEntity<Double> getReservationPrice(@PathVariable final String id)
    {
        return facade.getReservationPrice(id);
    }

    @GetMapping("/getAllTouristicPlaceStaysAvailableInDate")
    public ResponseEntity<List<StayDto>> getAllTouristicPlaceStaysAvailableInDate(@RequestParam final String touristicPlaceId,
                                                                                  @RequestParam final LocalDate dateFrom,
                                                                                  @RequestParam final LocalDate dateTo)
    {
        return facade.getAllTouristicPlaceStaysAvailableInDate(touristicPlaceId, dateFrom, dateTo);
    }

    @GetMapping("/getAllTouristicPlaceAttractionsAvailableInDate")
    public ResponseEntity<List<Attraction>> getAllTouristicPlaceAttractionsAvailableInDate(@RequestParam final String touristicPlaceId,
                                                                                           @RequestParam final LocalDate dateFrom,
                                                                                           @RequestParam final LocalDate dateTo)
    {
        return facade.getAllTouristicPlaceAttractionsAvailableInDate(touristicPlaceId, dateFrom, dateTo);
    }

    @GetMapping("/checkPayment")
    public ResponseEntity<ReservationDto> checkPayment(@RequestParam(required = false) final String reservationId,
                                                       @RequestParam final ReservationMode[] modes)
    {
        return facade.checkPayment(reservationId, modes);
    }

    @PostMapping("/getAllByAccountId")
    public ResponseEntity<List<ReservationDto>> getAllReservationsByAccountId(@RequestBody final ReservationStatus[] statuses)
    {
        return facade.getAllReservationsByAccountId(statuses);
    }

    @PostMapping("/getAllByTouristicPlaceId")
    public ResponseEntity<List<ReservationDto>> getAllReservationsByTouristicPlaceId(@RequestParam final String touristicPlaceId,
                                                                                     @RequestBody final ReservationStatus[] statuses)
    {
        return facade.getAllReservationsByTouristicPlaceId(touristicPlaceId, statuses);
    }

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestParam final String stayId,
                                                         @RequestParam final LocalDate dateFrom,
                                                         @RequestParam final LocalDate dateTo)
    {
        return facade.createReservation(stayId, dateFrom, dateTo);
    }

    @PostMapping("/createAttraction")
    public ResponseEntity<ReservationAttraction> createReservationAttraction(@RequestParam final String id,
                                                                             @RequestParam final String attractionId,
                                                                             @RequestParam final LocalDate dateFrom,
                                                                             @RequestParam final LocalDate dateTo,
                                                                             @RequestParam final String hourFrom,
                                                                             @RequestParam final String hourTo,
                                                                             @RequestParam(required = false) final Integer people,
                                                                             @RequestParam(required = false) final Integer items,
                                                                             @RequestParam(required = false) final String message)
    {
        return facade.createReservationAttraction(id, attractionId, dateFrom, dateTo, hourFrom, hourTo, people, items, message);
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<String> payForReservation(@PathVariable final String id,
                                                    @RequestParam final PaymentMethod method,
                                                    @RequestParam final ReservationMode mode,
                                                    @RequestParam(required = false) final LocalDate dateTo,
                                                    @RequestBody(required = false) final List<ReservationAttraction> reservationAttractions)
    {
        return facade.payForReservation(id, method, mode, dateTo, reservationAttractions);
    }

    @PostMapping("/makePayOnSite/{id}")
    public ResponseEntity<ReservationDto> makePayForReservationOnSite(@PathVariable final String id,
                                                                      @RequestParam final ReservationMode mode,
                                                                      @RequestParam(required = false) final LocalDate dateTo,
                                                                      @RequestBody(required = false) final List<ReservationAttraction> reservationAttractions)
    {
        return facade.makePayForReservationOnSite(id, mode, dateTo, reservationAttractions);
    }

    @PostMapping("payOnSite/{id}")
    public ResponseEntity<ReservationDto> payForReservationOnSite(@PathVariable final String id,
                                                                  @RequestParam final ReservationMode mode,
                                                                  @RequestBody(required = false) final List<ReservationAttraction> reservationAttractions)
    {
        return facade.payForReservationOnSite(id, mode, reservationAttractions);
    }

    @PutMapping("/updateDetails/{id}")
    public ResponseEntity<ReservationDto> updateReservationDetails(@PathVariable final String id,
                                                                   @RequestParam final String request)
    {
        return facade.updateReservationDetails(id, request);
    }

    @PutMapping("/updateOpinion/{id}")
    public ResponseEntity<ReservationDto> updateReservationOpinion(@PathVariable final String id,
                                                                   @RequestParam final Double rating,
                                                                   @RequestParam final String opinion)
    {
        return facade.updateReservationOpinion(id, rating, opinion);
    }

    @PutMapping("/updateDateTo/{id}")
    public ResponseEntity<ReservationDto> updateReservationDateTo(@PathVariable final String id,
                                                                  @RequestParam final LocalDate dateTo)
    {
        return facade.updateReservationDateTo(id, dateTo);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable final String id)
    {
        return facade.cancelReservation(id);
    }

    @PutMapping("/cancelAttraction")
    public ResponseEntity<?> cancelReservationAttraction(@RequestParam final String reservationAttractionId)
    {
        return facade.cancelReservationAttraction(reservationAttractionId);
    }

    @DeleteMapping("/deleteAttraction")
    public ResponseEntity<?> deleteReservationAttraction(@RequestParam final String reservationAttractionId)
    {
        return facade.deleteReservationAttraction(reservationAttractionId);
    }
}
