package com.turi.address.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AddressTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Address.class)
            .verify();
    }
}
