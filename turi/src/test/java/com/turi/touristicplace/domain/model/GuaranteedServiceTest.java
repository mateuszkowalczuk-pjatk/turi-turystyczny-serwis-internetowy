package com.turi.touristicplace.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class GuaranteedServiceTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
                .forClass(GuaranteedService.class)
                .verify();
    }
}