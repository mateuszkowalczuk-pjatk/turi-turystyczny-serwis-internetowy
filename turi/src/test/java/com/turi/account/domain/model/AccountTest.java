package com.turi.account.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AccountTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Account.class)
            .verify();
    }
}
