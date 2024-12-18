package com.turi.image.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ImageTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Image.class)
            .verify();
    }
}
