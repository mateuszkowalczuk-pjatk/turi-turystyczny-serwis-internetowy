package com.turi.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "premium.offer")
public class PremiumOfferProperties
{
    private double price;
    private int length;
}
