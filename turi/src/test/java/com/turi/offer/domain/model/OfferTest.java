package com.turi.offer.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class OfferTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Offer.class)
            .verify();
    }
}
