package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.port.PaymentRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class PaymentRepositoryTest
{
    @Autowired
    private PaymentRepository repository;
}
