package com.turi.reservation.infrastructure.adapter.repository;

import com.turi.reservation.domain.model.ReservationAttraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationAttractionRepositoryDao extends JpaRepository<ReservationAttraction, Long>
{

}
