package com.turi.payment.domain.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

class PaymentTest
{
    @Test
    void equalsContract()
    {
        EqualsVerifier.simple()
            .forClass(Payment.class).suppress(Warning.BIGDECIMAL_EQUALITY)
            .verify();
    }
}