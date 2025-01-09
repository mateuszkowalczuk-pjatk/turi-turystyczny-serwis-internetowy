package com.turi.reservation.infrastructure.adapter.interfaces;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/reservation", produces = "application/json")
public class ReservationRestController
{
    private final ReservationFacade facade;
}
