package com.turi.payment.infrastructure.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepositoryDao extends JpaRepository<PaymentEntity, Long>
{

}
