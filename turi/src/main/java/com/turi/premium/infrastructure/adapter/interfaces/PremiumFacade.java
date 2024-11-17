package com.turi.premium.infrastructure.adapter.interfaces;

import com.turi.premium.domain.port.PremiumService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PremiumFacade
{
    private final PremiumService service;
}
