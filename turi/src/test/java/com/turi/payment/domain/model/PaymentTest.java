package com.turi.payment.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PaymentTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Payment.class)
            .verify();
    }
}
