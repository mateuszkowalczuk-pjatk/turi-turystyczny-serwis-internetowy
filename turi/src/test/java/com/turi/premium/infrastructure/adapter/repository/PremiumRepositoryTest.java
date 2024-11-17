package com.turi.premium.infrastructure.adapter.repository;

import com.turi.premium.domain.port.PremiumRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class PremiumRepositoryTest
{
    @Autowired
    private PremiumRepository repository;
}
