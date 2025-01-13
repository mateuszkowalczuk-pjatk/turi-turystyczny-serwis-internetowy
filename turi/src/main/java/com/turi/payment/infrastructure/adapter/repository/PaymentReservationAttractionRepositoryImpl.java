package com.turi.payment.infrastructure.adapter.repository;

import com.turi.payment.domain.port.PaymentReservationAttractionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PaymentReservationAttractionRepositoryImpl implements PaymentReservationAttractionRepository
{
    private final PaymentReservationAttractionRepositoryDao repositoryDao;

    @Override
    public Boolean isPaymentForReservationAttraction(final Long paymentId, final Long reservationAttractionId)
    {
        return repositoryDao
                .findByPaymentIdAndReservationAttractionId(paymentId, reservationAttractionId)
                .orElse(null) != null;
    }

    @Override
    public void insert(final Long paymentId, final Long reservationAttractionId)
    {
        final var entity = PaymentReservationAttractionEntity.of(paymentId, reservationAttractionId);

        repositoryDao.saveAndFlush(entity);
    }
}
