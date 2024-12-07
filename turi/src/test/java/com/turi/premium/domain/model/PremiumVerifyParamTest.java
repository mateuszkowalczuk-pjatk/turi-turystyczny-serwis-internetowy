package com.turi.premium.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PremiumVerifyParamTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(PremiumVerifyParam.class)
            .verify();
    }
}
