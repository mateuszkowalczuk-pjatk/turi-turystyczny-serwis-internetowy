package com.turi.payment.infrastructure.adapter.interfaces;

import com.turi.testhelper.annotation.RestControllerTest;
import org.springframework.beans.factory.annotation.Autowired;

@RestControllerTest
class PaymentFacadeTest
{
    @Autowired(required = false)
    private PaymentFacade facade;
}
