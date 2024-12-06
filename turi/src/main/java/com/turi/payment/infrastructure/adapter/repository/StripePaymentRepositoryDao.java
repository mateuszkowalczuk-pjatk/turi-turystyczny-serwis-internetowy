package com.turi.payment.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StripePaymentRepositoryDao extends JpaRepository<StripePaymentEntity, Long>
{
    Optional<StripePaymentEntity> findByIntent(final String intent);
}
