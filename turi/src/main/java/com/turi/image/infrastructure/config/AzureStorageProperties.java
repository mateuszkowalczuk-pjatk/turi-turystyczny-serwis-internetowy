package com.turi.image.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "azure.storage")
public class AzureStorageProperties
{
    private String accountName;
    private String accountKey;
    private String containerName;
}
