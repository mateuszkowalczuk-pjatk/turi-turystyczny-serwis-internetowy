package com.turi.offer.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class FavouriteTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Favourite.class)
            .verify();
    }
}
