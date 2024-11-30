package com.turi.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SecurityProperties.class, StripeProperties.class, PremiumOfferProperties.class})
public class PropertiesConfig
{

}
