package com.turi.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "premium")
public class PremiumProperties
{
    private double price;
    private int length;
}
