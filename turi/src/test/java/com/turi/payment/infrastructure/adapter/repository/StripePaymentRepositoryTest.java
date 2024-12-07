package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.port.StripePaymentRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class StripePaymentRepositoryTest
{
    @Autowired
    private StripePaymentRepository repository;
}
