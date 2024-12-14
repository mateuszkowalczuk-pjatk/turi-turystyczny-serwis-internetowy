package com.turi.premium.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PremiumTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Premium.class)
            .verify();
    }
}
