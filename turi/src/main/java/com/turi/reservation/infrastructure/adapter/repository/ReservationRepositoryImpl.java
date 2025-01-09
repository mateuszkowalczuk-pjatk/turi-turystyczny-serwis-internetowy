package com.turi.reservation.infrastructure.adapter.repository;

import com.turi.reservation.domain.port.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository
{
    private final ReservationRepositoryDao repositoryDao;
}
