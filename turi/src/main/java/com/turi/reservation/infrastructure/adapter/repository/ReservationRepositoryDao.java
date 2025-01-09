package com.turi.reservation.infrastructure.adapter.repository;

import com.turi.reservation.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepositoryDao extends JpaRepository<Reservation, Long>
{

}
