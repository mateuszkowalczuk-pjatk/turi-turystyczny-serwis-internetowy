package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.exception.InvalidPaymentException;
import com.turi.payment.domain.exception.PaymentNotFoundException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.payment.domain.port.PaymentRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class PaymentRepositoryTest
{
    @Autowired
    private PaymentRepository repository;

    @Test
    void testPayment_FindAllByPremiumId()
    {
        final var payment = mockPayment();

        final var result = repository.findAllByPremiumId(payment.getPremiumId());

        assertNotNull(result);
        assertThat(result).isEqualTo(List.of(payment));
    }

    @Test
    void testPayment_FindAllByPremiumId_NothingFound()
    {
        final var result = repository.findAllByPremiumId(mockNewPayment().getPremiumId());

        assertThat(result).isEmpty();
    }

    @Test
    void testPayment_FindById()
    {
        final var payment = mockPayment();

        final var result = repository.findById(payment.getPaymentId());

        assertNotNull(result);
        assertThat(result).isEqualTo(payment);
    }

    @Test
    void testPayment_FindById_NotFound()
    {
        assertThrows(PaymentNotFoundException.class, () -> repository.findById(mockNewPayment().getPaymentId()));
    }

    @Test
    void testPayment_FindByStripeId()
    {
        final var payment = mockPayment();

        final var result = repository.findByStripeId(payment.getStripeId());

        assertNotNull(result);
        assertThat(result).isEqualTo(payment);
    }

    @Test
    void testPayment_FindByStripeId_NotFound()
    {
        assertNull(repository.findByStripeId(mockNewPayment().getStripeId()));
    }

    @Test
    void testPayment_Insert()
    {
        final var payment = mockNewPayment();

        final var paymentId = repository.insert(payment);

        final var result = repository.findById(paymentId);

        assertNotNull(result);
        assertThat(result).isEqualTo(payment);
    }

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

    @Test
    void testPayment_Update()
    {
        final var payment = mockPayment();

        payment.setStripePaymentIntent(mockNewPayment().getStripePaymentIntent());

        repository.update(payment.getPaymentId(), payment);

        final var result = repository.findById(payment.getPaymentId());

        assertNotNull(result);
        assertThat(result).isEqualTo(payment);
    }

    @Test
    void testPayment_Update_PaymentNotFound()
    {
        final var payment = mockPayment();

        payment.setPaymentId(mockNewPayment().getPaymentId());

        repository.update(payment.getPaymentId(), payment);

        assertThrows(PaymentNotFoundException.class, () -> repository.findById(payment.getPaymentId()));
    }

    @Test
    void testPayment_Update_WithoutRequiredPremiumIdField()
    {
        final var payment = mockPayment();

        payment.setPremiumId(null);

        assertThrows(InvalidPaymentException.class, () -> repository.update(payment.getPaymentId(), payment));
    }

    @Test
    void testPayment_Update_WithoutRequiredStripeIdField()
    {
        final var payment = mockPayment();

        payment.setStripeId(null);

        assertThrows(InvalidPaymentException.class, () -> repository.update(payment.getPaymentId(), payment));
    }

    @Test
    void testPayment_Update_WithoutRequiredAmountField()
    {
        final var payment = mockPayment();

        payment.setAmount(null);

        assertThrows(InvalidPaymentException.class, () -> repository.update(payment.getPaymentId(), payment));
    }

    @Test
    void testPayment_Update_WithoutRequiredPaymentDateField()
    {
        final var payment = mockPayment();

        payment.setPaymentDate(null);

        assertThrows(InvalidPaymentException.class, () -> repository.update(payment.getPaymentId(), payment));
    }

    @Test
    void testPayment_Update_WithoutRequiredMethodField()
    {
        final var payment = mockPayment();

        payment.setMethod(null);

        assertThrows(InvalidPaymentException.class, () -> repository.update(payment.getPaymentId(), payment));
    }

    @Test
    void testPayment_Update_WithoutRequiredStatusField()
    {
        final var payment = mockPayment();

        payment.setStatus(null);

        assertThrows(InvalidPaymentException.class, () -> repository.update(payment.getPaymentId(), payment));
    }

    private Payment mockPayment()
    {
        return Payment.builder()
                .withPaymentId(1L)
                .withPremiumId(1L)
                .withStripeId("sample_stripe_session_id")
                .withStripePaymentIntent("sample_payment_intent")
                .withAmount(BigDecimal.valueOf(100.25))
                .withPaymentDate(LocalDateTime.of(2024, 10, 10, 12, 0, 0))
                .withMethod(PaymentMethod.CARD)
                .withStatus(PaymentStatus.SUCCEEDED)
                .build();
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
