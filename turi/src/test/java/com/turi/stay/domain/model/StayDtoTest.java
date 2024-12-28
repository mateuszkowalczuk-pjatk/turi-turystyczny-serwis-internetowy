package com.turi.stay.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class StayDtoTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(StayDto.class).suppress(Warning.BIGDECIMAL_EQUALITY)
            .verify();
    }
}
