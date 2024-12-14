package com.turi.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "ceidg")
public class CeidgProperties
{
    private String url;
    private String apiKey;
}
