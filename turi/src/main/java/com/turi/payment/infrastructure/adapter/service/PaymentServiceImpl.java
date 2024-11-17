package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.port.PaymentRepository;
import com.turi.payment.domain.port.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService
{
    private final PaymentRepository repository;
}
