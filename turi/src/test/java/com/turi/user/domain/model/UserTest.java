package com.turi.user.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class UserTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(User.class)
            .verify();
    }
}
