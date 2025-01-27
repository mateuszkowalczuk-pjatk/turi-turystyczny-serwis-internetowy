package com.turi.infrastructure.config;

import com.turi.premium.infrastructure.config.CeidgProperties;
import com.turi.payment.infrastructure.config.StripeProperties;
import com.turi.premium.infrastructure.config.PremiumProperties;
import com.turi.authentication.infrastructure.config.SecurityProperties;
import com.turi.image.infrastructure.config.AzureStorageProperties;
import com.turi.image.infrastructure.config.ImageStorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        CeidgProperties.class,
        StripeProperties.class,
        PremiumProperties.class,
        SecurityProperties.class,
        AzureStorageProperties.class,
        ImageStorageProperties.class,
        AppProperties.class
})
public class PropertiesConfig
{

}
