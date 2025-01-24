package com.turi.reservation.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ReservationDtoTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(ReservationDto.class)
            .verify();
    }
}
