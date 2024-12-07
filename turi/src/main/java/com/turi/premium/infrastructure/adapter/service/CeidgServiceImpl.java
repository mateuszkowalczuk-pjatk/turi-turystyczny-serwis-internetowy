package com.turi.premium.infrastructure.adapter.service;

import com.turi.infrastructure.properties.PremiumProperties;
import com.turi.premium.domain.model.PremiumCompanyParam;
import com.turi.premium.domain.port.CeidgService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CeidgServiceImpl implements CeidgService
{
    private final PremiumProperties properties;

    @Override
    public boolean verifyCompany(final PremiumCompanyParam params)
    {
        return true;
    }
}
