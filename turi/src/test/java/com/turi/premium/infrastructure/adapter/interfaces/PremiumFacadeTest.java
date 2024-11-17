package com.turi.premium.infrastructure.adapter.interfaces;

import com.turi.testhelper.annotation.RestControllerTest;
import org.springframework.beans.factory.annotation.Autowired;

@RestControllerTest
class PremiumFacadeTest
{
    @Autowired(required = false)
    private PremiumFacade facade;
}
