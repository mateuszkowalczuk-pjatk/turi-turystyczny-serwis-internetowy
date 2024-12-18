package com.turi.touristicplace.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class TouristicPlaceTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(TouristicPlace.class)
            .verify();
    }
}
