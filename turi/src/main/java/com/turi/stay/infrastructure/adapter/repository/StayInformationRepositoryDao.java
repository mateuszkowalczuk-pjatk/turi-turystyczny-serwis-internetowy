package com.turi.stay.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StayInformationRepositoryDao extends JpaRepository<StayInformationEntity, Long>
{
    List<StayInformationEntity> findAllByStayId(final Long stayId);
}
