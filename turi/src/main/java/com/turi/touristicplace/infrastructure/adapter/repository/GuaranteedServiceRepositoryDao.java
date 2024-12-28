package com.turi.touristicplace.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuaranteedServiceRepositoryDao extends JpaRepository<GuaranteedServiceEntity, Long>
{
    List<GuaranteedServiceEntity> findAllByTouristicPlaceId(final Long touristicPlaceId);

    Optional<GuaranteedServiceEntity> findByTouristicPlaceIdAndService(final Long touristicPlaceId, final String service);
}
