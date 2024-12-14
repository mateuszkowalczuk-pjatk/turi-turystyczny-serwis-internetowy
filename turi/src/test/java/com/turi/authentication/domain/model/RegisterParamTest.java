package com.turi.authentication.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RegisterParamTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(RegisterParam.class)
            .verify();
    }
}
