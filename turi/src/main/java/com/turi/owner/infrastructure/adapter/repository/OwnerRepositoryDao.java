package com.turi.owner.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepositoryDao extends JpaRepository<OwnerEntity, Long>
{

}
