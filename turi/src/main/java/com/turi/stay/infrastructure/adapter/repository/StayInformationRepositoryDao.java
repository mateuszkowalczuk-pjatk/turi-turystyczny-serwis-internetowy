package com.turi.stay.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayInformationRepositoryDao extends JpaRepository<StayInformationEntity, Long>
{

}
