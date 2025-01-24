package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.exception.InvalidPaymentException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.payment.domain.port.PaymentRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RepositoryTest
class PaymentRepositoryTest
{
    @Autowired
    private PaymentRepository repository;

    @Test
    void testPayment_Insert_WithoutRequiredPremiumIdField()
    {
        final var payment = mockNewPayment();
        payment.setPremiumId(null);

        assertThrows(InvalidPaymentException.class, () -> repository.insert(payment));
    }

    @Test
    void testPayment_Insert_WithoutRequiredStripeIdField()
    {
        final var payment = mockNewPayment();
        payment.setPremiumId(null);

        assertThrows(InvalidPaymentException.class, () -> repository.insert(payment));
    }

    @Test
    void testPayment_Insert_WithoutRequiredAmountField()
    {
        final var payment = mockNewPayment();
        payment.setAmount(null);

        assertThrows(InvalidPaymentException.class, () -> repository.insert(payment));
    }

    @Test
    void testPayment_Insert_WithoutRequiredPaymentDateField()
    {
        final var payment = mockNewPayment();
        payment.setPaymentDate(null);

        assertThrows(InvalidPaymentException.class, () -> repository.insert(payment));
    }

    @Test
    void testPayment_Insert_WithoutRequiredMethodField()
    {
        final var payment = mockNewPayment();
        payment.setMethod(null);

        assertThrows(InvalidPaymentException.class, () -> repository.insert(payment));
    }

    @Test
    void testPayment_Insert_WithoutRequiredStatusField()
    {
        final var payment = mockNewPayment();
        payment.setStatus(null);

        assertThrows(InvalidPaymentException.class, () -> repository.insert(payment));
    }

    private Payment mockNewPayment()
    {
        return Payment.builder()
                .withPaymentId(2L)
                .withPremiumId(2L)
                .withStripeId("sample_stripe_other_session_id")
                .withStripePaymentIntent("sample_other_payment_intent")
                .withAmount(BigDecimal.valueOf(100.55))
                .withPaymentDate(LocalDateTime.of(2024, 10, 10, 12, 0, 0))
                .withMethod(PaymentMethod.CARD)
                .withStatus(PaymentStatus.SUCCEEDED)
                .build();
    }
}
