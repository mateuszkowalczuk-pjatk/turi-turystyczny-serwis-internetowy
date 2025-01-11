package com.turi.payment.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "stripe")
public class StripeProperties
{
    private String secretKey;
    private String successUrl;
    private String cancelUrl;
    private String webhookSecretKey;
}
