package com.turi.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PaymentMethod
{
    CARD(1, "CARD"),
    BLIK(2, "BLIK");

    private final int value;
    private final String name;

    public static PaymentMethod fromValue(final int value)
    {
        return Arrays.stream(PaymentMethod.values())
                .filter(method -> method.getValue() == value)
                .findFirst()
                .orElse(null);
    }

    public static int getValueOrDefault(final PaymentMethod method)
    {
        return method != null ? method.getValue() : 0;
    }
}
