package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.port.PaymentService;
import com.turi.testhelper.annotation.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class PaymentServiceTest
{
    @Autowired
    private PaymentService service;
}
