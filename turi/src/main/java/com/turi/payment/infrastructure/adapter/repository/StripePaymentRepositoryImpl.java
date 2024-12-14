package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.model.StripePayment;
import com.turi.payment.domain.port.StripePaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StripePaymentRepositoryImpl implements StripePaymentRepository
{
    private final StripePaymentRepositoryDao repositoryDao;

    @Override
    public StripePayment findByIntent(final String intent)
    {
        return repositoryDao.findByIntent(intent)
                .map(StripePayment::of)
                .orElse(null);
    }

    @Override
    public void insert(final StripePayment stripePayment)
    {
        final var entity = StripePaymentEntity.of(stripePayment);

        repositoryDao.saveAndFlush(entity);
    }
}
