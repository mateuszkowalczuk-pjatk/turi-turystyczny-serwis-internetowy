package com.turi.authentication.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RefreshTokenTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(RefreshToken.class)
            .verify();
    }
}
