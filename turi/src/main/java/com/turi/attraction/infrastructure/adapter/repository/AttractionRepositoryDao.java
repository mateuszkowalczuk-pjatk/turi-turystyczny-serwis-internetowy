package com.turi.attraction.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepositoryDao extends JpaRepository<AttractionEntity, Long>
{
    List<AttractionEntity> findAllByTouristicPlaceId(final Long touristicPlaceId);
}
