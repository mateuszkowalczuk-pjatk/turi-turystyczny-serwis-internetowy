package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.port.StripeService;
import com.turi.testhelper.annotation.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class StripeServiceTest
{
    @Autowired
    private StripeService service;
}
