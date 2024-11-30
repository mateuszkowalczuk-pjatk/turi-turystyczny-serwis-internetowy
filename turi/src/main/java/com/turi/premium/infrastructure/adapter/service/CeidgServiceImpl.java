package com.turi.premium.infrastructure.adapter.service;

import com.turi.premium.domain.model.PremiumCompanyParam;
import com.turi.premium.domain.port.CeidgService;
import org.springframework.stereotype.Service;

@Service
public class CeidgServiceImpl implements CeidgService
{
    @Override
    public boolean verifyCompany(final PremiumCompanyParam params)
    {
        return true;
    }
}
