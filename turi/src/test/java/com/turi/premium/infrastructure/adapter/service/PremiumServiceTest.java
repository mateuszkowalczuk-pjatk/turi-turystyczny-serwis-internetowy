package com.turi.premium.infrastructure.adapter.service;

import com.turi.premium.domain.port.PremiumService;
import com.turi.testhelper.annotation.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class PremiumServiceTest
{
    @Autowired
    private PremiumService service;
}
