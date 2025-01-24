package com.turi.payment.infrastructure.adapter.service;

import com.turi.payment.domain.exception.PaymentForPremiumFailedException;
import com.turi.payment.domain.exception.PaymentForReservationFailedException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentName;
import com.turi.payment.domain.model.PaymentStatus;
import com.turi.payment.domain.model.StripePaymentResponse;
import com.turi.payment.domain.port.PaymentRepository;
import com.turi.payment.domain.port.PaymentReservationAttractionService;
import com.turi.payment.domain.port.PaymentService;
import com.turi.payment.domain.port.StripeService;
import com.turi.reservation.domain.model.ReservationAttraction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService
{
    private final StripeService stripeService;
    private final PaymentRepository repository;
    private final PaymentReservationAttractionService paymentReservationAttractionService;

    @Override
    public Boolean isPaymentForPremiumSucceeded(final Long premiumId)
    {
        final var payment = repository.findAllByPremiumId(premiumId).stream()
                .max(Comparator.comparingLong(Payment::getPaymentId))
                .orElse(null);

        if (payment == null)
        {
            return false;
        }

        final var stripePayment = stripeService.getByIntent(payment.getStripePaymentIntent());

        if (stripePayment == null)
        {
            return false;
        }

        payment.setPaymentDate(stripePayment.getPaymentDate());
        payment.setStatus(stripePayment.getStatus());

        repository.update(payment.getPaymentId(), payment);

        if (payment.getStatus() == PaymentStatus.FAILED)
        {
            throw new PaymentForPremiumFailedException(premiumId);
        }

        return payment.getStatus() == PaymentStatus.SUCCEEDED;
    }

    @Override
    public Boolean isPaymentForReservationSucceeded(final Long reservationId)
    {
        final var payment = repository.findAllByReservationId(reservationId).stream()
                .max(Comparator.comparingLong(Payment::getPaymentId))
                .orElse(null);

        if (payment == null)
        {
            return false;
        }

        final var stripePayment = stripeService.getByIntent(payment.getStripePaymentIntent());

        if (stripePayment == null)
        {
            return false;
        }

        payment.setPaymentDate(stripePayment.getPaymentDate());
        payment.setStatus(stripePayment.getStatus());

        repository.update(payment.getPaymentId(), payment);

        if (payment.getStatus() == PaymentStatus.FAILED)
        {
            throw new PaymentForReservationFailedException(reservationId);
        }

        return payment.getStatus() == PaymentStatus.SUCCEEDED;
    }

    @Override
    public String payForPremium(final Payment payment)
    {
        final var response = stripeService.checkout(payment, PaymentName.PREMIUM);

        repository.insert(preparePaymentForInsert(payment, response));

        return response.getUrl();
    }

    @Override
    public String payForReservation(final Payment payment, final List<ReservationAttraction> reservationAttractions)
    {
        final var response = stripeService.checkout(payment, PaymentName.RESERVATION);

        final var paymentId = repository.insert(preparePaymentForInsert(payment, response));

        reservationAttractions.forEach(reservationAttraction -> paymentReservationAttractionService.create(paymentId, reservationAttraction.getReservationAttractionId()));

        return response.getUrl();
    }

    private Payment preparePaymentForInsert(final Payment payment, final StripePaymentResponse response)
    {
        payment.setStripeId(response.getStripeId());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(response.getStatus());

        return payment;
    }

    @Override
    public void handleStripeWebhook(final String payload, final String sigHeader)
    {
        final var response = stripeService.webhook(payload, sigHeader);

        if (response != null)
        {
            final var payment = repository.findByStripeId(response.getStripeId());
            payment.setStripePaymentIntent(response.getStripePaymentIntent());
            payment.setPaymentDate(LocalDateTime.now());

            repository.update(payment.getPaymentId(), payment);
        }
    }
}
