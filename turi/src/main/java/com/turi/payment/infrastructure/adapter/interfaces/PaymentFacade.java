package com.turi.payment.infrastructure.adapter.interfaces;

import com.turi.infrastructure.exception.BadRequestParameterException;
import com.turi.payment.domain.model.Payment;
import com.turi.payment.domain.model.PaymentMethod;
import com.turi.payment.domain.port.PaymentService;
import com.turi.reservation.domain.model.ReservationAttraction;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@AllArgsConstructor
public class PaymentFacade
{
    private final PaymentService service;

    public Boolean isPaymentForPremiumSucceeded(final Long premiumId)
    {
        if (premiumId == null)
        {
            throw new BadRequestParameterException("Parameter premiumId is required.");
        }

        return service.isPaymentForPremiumSucceeded(premiumId);
    }

    public Boolean isPaymentForReservationSucceeded(final Long reservationId)
    {
        if (reservationId == null)
        {
            throw new BadRequestParameterException("Parameter reservationId is required.");
        }

        return service.isPaymentForReservationSucceeded(reservationId);
    }

    public String payForPremium(final Long premiumId, final Double price, final PaymentMethod method)
    {
        if (premiumId == null || price == null || method == null)
        {
            throw new BadRequestParameterException("Parameters premiumId, price and method are required.");
        }

        final var payment = Payment.builder()
                .withPremiumId(premiumId)
                .withAmount(BigDecimal.valueOf(price))
                .withMethod(method)
                .build();

        return service.payForPremium(payment);
    }

    public String payForReservation(final Long reservationId, final Double price, final PaymentMethod method, final List<ReservationAttraction> reservationAttractions)
    {
        if (reservationId == null || price == null || method == null)
        {
            throw new BadRequestParameterException("Parameters reservationId, price and method are required.");
        }

        final var payment = Payment.builder()
                .withReservationId(reservationId)
                .withAmount(BigDecimal.valueOf(price))
                .withMethod(method)
                .build();

        return service.payForReservation(payment, reservationAttractions);
    }

    public ResponseEntity<?> handleStripeWebhook(final String payload, final String sigHeader)
    {
        if (payload == null || sigHeader == null)
        {
            throw new BadRequestParameterException("Parameters payload and sigHeader are required.");
        }

        service.handleStripeWebhook(payload, sigHeader);

        return ResponseEntity.ok().build();
    }
}
