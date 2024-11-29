package com.turi.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PaymentStatus
{
    PENDING(1, "PENDING"),
    SUCCESS(2, "SUCCESS"),
    FAILED(3, "FAILED");

    private final int value;
    private final String name;

    public static PaymentStatus fromValue(final int value)
    {
        return Arrays.stream(PaymentStatus.values())
                .filter(status -> status.getValue() == value)
                .findFirst()
                .orElse(null);
    }

    public static int getValueOrDefault(final PaymentStatus status)
    {
        return status != null ? status.getValue() : 0;
    }
}
