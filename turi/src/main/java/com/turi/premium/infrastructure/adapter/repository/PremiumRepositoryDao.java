package com.turi.premium.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumRepositoryDao extends JpaRepository<PremiumEntity, Long>
{

}
