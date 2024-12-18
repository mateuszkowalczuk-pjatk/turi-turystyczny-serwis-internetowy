package com.turi.attraction.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepositoryDao extends JpaRepository<AttractionEntity, Long>
{

}
