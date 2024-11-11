package com.turi.user.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryDao extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByUsername(final String username);

    Optional<UserEntity> findByEmail(final String email);

    Optional<UserEntity> findByPasswordResetToken(final String passwordResetToken);
}
