package com.turi.premium.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PremiumRepositoryDao extends JpaRepository<PremiumEntity, Long>
{
    List<PremiumEntity> findAllByExpiresDateBeforeAndStatus(final LocalDate currentDate, final int status);

    Optional<PremiumEntity> findByAccountid(final Long accountId);

    Optional<PremiumEntity> findByLoginToken(final String loginToken);
}
