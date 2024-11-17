package com.turi.authentication.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class RefreshParamTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(RefreshParam.class)
            .verify();
    }
}
