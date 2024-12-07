package com.turi.premium.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PremiumLoginTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(PremiumLogin.class)
            .verify();
    }
}
