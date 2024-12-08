package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.exception.InvalidStripePaymentException;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.payment.domain.model.StripePayment;
import com.turi.payment.domain.port.StripePaymentRepository;
import com.turi.testhelper.annotation.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class StripePaymentRepositoryTest
{
    @Autowired
    private StripePaymentRepository repository;

    @Test
    void testStripePayment_FindByIntent()
    {
        final var stripePayment = mockStripePayment();

        final var result = repository.findByIntent(stripePayment.getIntent());

        assertNotNull(result);
        assertThat(result).isEqualTo(stripePayment);
    }

    @Test
    void testStripePayment_FindByIntent_NotFound()
    {
        assertNull(repository.findByIntent(mockNewStripePayment().getIntent()));
    }

    @Test
    void testStripePayment_Insert()
    {
        final var stripePayment = mockNewStripePayment();

        repository.insert(stripePayment);

        final var result = repository.findByIntent(stripePayment.getIntent());

        assertNotNull(result);
        assertThat(result).isEqualTo(stripePayment);
    }

    @Test
    void testStripePayment_Insert_WithoutRequiredIntentField()
    {
        final var stripePayment = mockNewStripePayment();
        stripePayment.setIntent(null);

        assertThrows(InvalidStripePaymentException.class, () -> repository.insert(stripePayment));
    }

    @Test
    void testStripePayment_Insert_WithoutRequiredStatusField()
    {
        final var stripePayment = mockNewStripePayment();
        stripePayment.setStatus(null);

        assertThrows(InvalidStripePaymentException.class, () -> repository.insert(stripePayment));
    }

    @Test
    void testStripePayment_Insert_WithoutRequiredPaymentDateField()
    {
        final var stripePayment = mockNewStripePayment();
        stripePayment.setPaymentDate(null);

        assertThrows(InvalidStripePaymentException.class, () -> repository.insert(stripePayment));
    }

    private StripePayment mockStripePayment()
    {
        return StripePayment.builder()
                .withStripePaymentId(1L)
                .withIntent("sample_payment_intent")
                .withStatus(PaymentStatus.SUCCEEDED)
                .withPaymentDate(LocalDateTime.of(2024, 10, 10, 12, 0, 0))
                .build();
    }

    private StripePayment mockNewStripePayment()
    {
        return StripePayment.builder()
                .withStripePaymentId(2L)
                .withIntent("sample_other_payment_intent")
                .withStatus(PaymentStatus.SUCCEEDED)
                .withPaymentDate(LocalDateTime.of(2024, 10, 10, 14, 0, 0))
                .build();
    }
}
