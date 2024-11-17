package com.turi.premium.infrastructure.adapter.service;

import com.turi.premium.domain.port.PremiumRepository;
import com.turi.premium.domain.port.PremiumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PremiumServiceImpl implements PremiumService
{
    private final PremiumRepository repository;
}
