package com.turi.touristicplace.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TouristicPlaceRepositoryDao extends JpaRepository<TouristicPlaceEntity, Long>
{
    Optional<TouristicPlaceEntity> findByPremiumId(final Long premiumId);

    Optional<TouristicPlaceEntity> findByAddressId(final Long addressId);
}
