package com.turi.reservation.infrastructure.adapter.service;

import com.turi.reservation.domain.port.ReservationRepository;
import com.turi.reservation.domain.port.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService
{
    private final ReservationRepository repository;
}
