package com.turi.reservation.infrastructure.adapter.service;

import com.turi.reservation.domain.port.ReservationAttractionRepository;
import com.turi.reservation.domain.port.ReservationAttractionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReservationAttractionServiceImpl implements ReservationAttractionService
{
    private final ReservationAttractionRepository repository;
}
