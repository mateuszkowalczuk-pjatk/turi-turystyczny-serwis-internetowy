package com.turi.offer.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class SearchTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Search.class)
            .verify();
    }
}
