package com.turi.infrastructure.config;

import com.turi.infrastructure.properties.PremiumProperties;
import com.turi.infrastructure.properties.SecurityProperties;
import com.turi.infrastructure.properties.StripeProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        SecurityProperties.class,
        StripeProperties.class,
        PremiumProperties.class
})
public class PropertiesConfig
{

}
