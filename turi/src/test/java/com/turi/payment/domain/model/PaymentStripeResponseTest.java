package com.turi.payment.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PaymentStripeResponseTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(PaymentStripeResponse.class)
            .verify();
    }
}
