package com.turi.premium.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PremiumCompanyParamTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(PremiumCompanyParam.class)
            .verify();
    }
}
