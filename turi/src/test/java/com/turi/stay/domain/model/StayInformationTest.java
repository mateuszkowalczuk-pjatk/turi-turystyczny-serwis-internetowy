package com.turi.stay.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class StayInformationTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(StayInformation.class)
            .verify();
    }
}
