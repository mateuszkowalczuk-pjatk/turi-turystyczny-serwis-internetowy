package com.turi.reservation.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepositoryDao extends JpaRepository<ReservationEntity, Long>
{

}
