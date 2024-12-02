package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.exception.PaymentNotFoundException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.port.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository
{
    private final PaymentRepositoryDao repositoryDao;

    @Override
    public List<Payment> findAllByPremiumId(final Long premiumId)
    {
        return repositoryDao.findAllByPremiumId(premiumId).stream()
                .map(Payment::of)
                .toList();
    }

    @Override
    public Payment findById(final Long id)
    {
        return repositoryDao.findById(id)
                .map(Payment::of)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    @Override
    public Payment findByStripeId(final Long stripeId)
    {
        return repositoryDao.findByStripeId(stripeId)
                .map(Payment::of)
                .orElse(null);
    }

    @Override
    public Long insert(final Payment payment)
    {
        final var entity = PaymentEntity.of(payment);

        return repositoryDao.saveAndFlush(entity).getPaymentId();
    }

    @Override
    public void update(final Long id, final Payment payment)
    {
        final var paymentEntity = repositoryDao.findById(id).orElse(null);

        final var entity = PaymentEntity.of(payment);

        Optional.ofNullable(paymentEntity).ifPresent(e -> {
            e.setStatus(entity.getStatus());

            repositoryDao.saveAndFlush(paymentEntity);
        });
    }
}
