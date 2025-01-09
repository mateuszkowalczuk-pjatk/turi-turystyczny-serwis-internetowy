package com.turi.reservation.infrastructure.adapter.repository;

import com.turi.reservation.domain.port.ReservationAttractionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ReservationAttractionRepositoryImpl implements ReservationAttractionRepository
{
    private final ReservationAttractionRepositoryDao repositoryDao;
}
