package com.turi.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityProperties
{
    private String secretKey;
    private Long accessTokenExpirationTime;
    private Long refreshTokenExpirationTime;
}
