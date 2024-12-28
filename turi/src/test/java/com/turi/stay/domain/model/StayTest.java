package com.turi.stay.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class StayTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Stay.class).suppress(Warning.BIGDECIMAL_EQUALITY)
            .verify();
    }
}
