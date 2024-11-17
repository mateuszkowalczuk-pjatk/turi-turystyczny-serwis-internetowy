package com.turi.payment.infrastructure.adapter.interfaces;

import com.turi.payment.domain.port.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentFacade
{
    private final PaymentService service;
}
