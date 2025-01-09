package com.turi.reservation.infrastructure.adapter.interfaces;

import com.turi.reservation.domain.port.ReservationAttractionService;
import com.turi.reservation.domain.port.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservationFacade
{
    private final ReservationService reservationService;
    private final ReservationAttractionService reservationAttractionService;
}
