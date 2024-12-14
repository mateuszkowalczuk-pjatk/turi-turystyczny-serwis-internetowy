package com.turi.authentication.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class LoginParamTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(LoginParam.class)
            .verify();
    }
}
