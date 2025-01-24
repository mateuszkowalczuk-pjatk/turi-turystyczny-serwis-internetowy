package com.turi.payment.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepositoryDao extends JpaRepository<PaymentEntity, Long>
{
    List<PaymentEntity> findAllByPremiumId(final Long premiumId);

    List<PaymentEntity> findAllByReservationId(final Long reservationId);

    Optional<PaymentEntity> findByStripeId(final String stripeId);
}
