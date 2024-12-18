package com.turi.touristicplace.infrastructure.adapter.service;

import com.turi.touristicplace.domain.port.GuaranteedServiceRepository;
import com.turi.touristicplace.domain.port.GuaranteedServiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuaranteedServiceServiceImpl implements GuaranteedServiceService
{
    private final GuaranteedServiceRepository repository;
}
