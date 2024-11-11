package com.turi.authentication.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepositoryDao extends JpaRepository<RefreshTokenEntity, Long>
{
    Optional<RefreshTokenEntity> findByToken(final String token);

    void deleteByToken(final String token);

    void deleteAllByExpiresAtBefore(final LocalDateTime expiresat);
}
