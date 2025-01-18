package com.turi.reservation.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ReservationTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
                .forClass(Reservation.class)
                .verify();
    }
}