package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.exception.UniquePaymentReservationAttractionException;
import com.turi.payment.domain.port.PaymentReservationAttractionRepository;
import com.turi.payment.domain.port.PaymentReservationAttractionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentReservationAttractionServiceImpl implements PaymentReservationAttractionService
{
    private final PaymentReservationAttractionRepository repository;

    @Override
    public void create(final Long paymentId, final Long reservationAttractionId)
    {
        if (isPaymentForReservationAttraction(paymentId, reservationAttractionId))
        {
            throw new UniquePaymentReservationAttractionException();
        }

        repository.insert(paymentId, reservationAttractionId);
    }

    public Boolean isPaymentForReservationAttraction(final Long paymentId, final Long reservationAttractionId)
    {
        return repository.isPaymentForReservationAttraction(paymentId, reservationAttractionId);
    }
}
