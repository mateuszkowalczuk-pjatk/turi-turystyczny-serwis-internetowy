package com.turi.infrastructure.config;

import com.turi.infrastructure.properties.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        SecurityProperties.class,
        StripeProperties.class,
        PremiumProperties.class,
        CeidgProperties.class,
        AzureStorageProperties.class,
        ImageStorageProperties.class
})
public class PropertiesConfig
{

}
