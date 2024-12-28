package com.turi.attraction.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class AttractionTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Attraction.class).suppress(Warning.BIGDECIMAL_EQUALITY)
            .verify();
    }
}
