package com.turi.touristicplace.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuaranteedServiceRepositoryDao extends JpaRepository<GuaranteedServiceEntity, Long>
{

}
