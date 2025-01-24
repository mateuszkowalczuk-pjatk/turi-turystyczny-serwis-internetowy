package com.turi.reservation.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepositoryDao extends JpaRepository<ReservationEntity, Long>
{
    List<ReservationEntity> findAllByStayId(final Long stayId);

    List<ReservationEntity> findAllByAccountId(final Long accountId);
}
