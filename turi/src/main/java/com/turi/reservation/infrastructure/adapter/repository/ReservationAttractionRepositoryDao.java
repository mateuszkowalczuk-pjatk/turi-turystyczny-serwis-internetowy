package com.turi.reservation.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationAttractionRepositoryDao extends JpaRepository<ReservationAttractionEntity, Long>
{
    List<ReservationAttractionEntity> findAllByReservationId(final Long reservationId);

    List<ReservationAttractionEntity> findAllByAttractionId(final Long attractionId);
}
