package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.port.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository
{
    private final PaymentRepositoryDao repositoryDao;
}
