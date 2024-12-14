package com.turi.premium.infrastructure.adapter.service;

import com.turi.premium.domain.model.PremiumCompanyParam;
import com.turi.premium.domain.port.CeidgService;
import com.turi.testhelper.annotation.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ServiceTest
class CeidgServiceTest
{
    @Autowired
    private CeidgService service;

    @Test
    void testCeidg_VerifyCompany()
    {
        final var params = PremiumCompanyParam.builder()
                .withCompanyName("anmeko")
                .withNip("5441482924")
                .build();

        assertTrue(service.verifyCompany(params));
    }

    @Test
    void testCeidg_VerifyCompany_InvalidNip()
    {
        final var params = PremiumCompanyParam.builder()
                .withCompanyName("marex")
                .withNip("5441481234")
                .build();

        assertFalse(service.verifyCompany(params));
    }
}
