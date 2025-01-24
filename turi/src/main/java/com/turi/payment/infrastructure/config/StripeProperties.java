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
    private String premiumSuccessUrl;
    private String premiumCancelUrl;
    private String reservationSuccessUrl;
    private String reservationCancelUrl;
    private String webhookSecretKey;
}
