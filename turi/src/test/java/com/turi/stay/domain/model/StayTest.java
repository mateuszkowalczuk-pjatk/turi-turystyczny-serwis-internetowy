package com.turi.stay.domain.model;

import com.turi.image.domain.model.Image;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class StayTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Stay.class)
            .verify();
    }
}
