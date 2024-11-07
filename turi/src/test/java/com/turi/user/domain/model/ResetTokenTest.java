package com.turi.user.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ResetTokenTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(ResetToken.class)
            .verify();
    }
}
