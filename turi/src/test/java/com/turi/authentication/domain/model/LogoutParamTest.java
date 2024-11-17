package com.turi.authentication.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class LogoutParamTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(LogoutParam.class)
            .verify();
    }
}
