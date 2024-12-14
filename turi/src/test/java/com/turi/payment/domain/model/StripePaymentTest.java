package com.turi.payment.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class StripePaymentTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(StripePayment.class)
            .verify();
    }
}
