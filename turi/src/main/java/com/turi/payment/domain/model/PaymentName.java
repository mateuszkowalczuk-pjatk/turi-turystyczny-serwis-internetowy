package com.turi.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentName
{
    PREMIUM("Subskrypcja konta Premium");

    private final String name;
}
