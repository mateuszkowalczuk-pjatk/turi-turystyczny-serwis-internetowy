package com.turi.payment.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class StripePaymentResponseTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(StripePaymentResponse.class)
            .verify();
    }
}
