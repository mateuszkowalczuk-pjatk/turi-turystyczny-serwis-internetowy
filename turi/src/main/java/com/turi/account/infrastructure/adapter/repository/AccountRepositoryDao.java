package com.turi.account.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepositoryDao extends JpaRepository<AccountEntity, Long>
{
    Optional<AccountEntity> findByAddressId(final Long addressId);

    Optional<AccountEntity> findByLogin(final String login);

    Optional<AccountEntity> findByEmail(final String email);

    Optional<AccountEntity> findByPhoneNumber(final String phoneNumber);
}
