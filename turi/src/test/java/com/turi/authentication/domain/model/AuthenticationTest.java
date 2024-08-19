package com.turi.authentication.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AuthenticationTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Authentication.class)
            .verify();
    }
}
