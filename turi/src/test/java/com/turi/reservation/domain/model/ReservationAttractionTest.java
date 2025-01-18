package com.turi.reservation.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ReservationAttractionTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
                .forClass(ReservationAttraction.class)
                .verify();
    }
}