package com.turi.premium.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PremiumOfferTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(PremiumOffer.class)
            .verify();
    }
}
