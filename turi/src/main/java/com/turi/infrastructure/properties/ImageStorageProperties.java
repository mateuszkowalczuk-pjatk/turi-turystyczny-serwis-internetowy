package com.turi.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "image.storage")
public class ImageStorageProperties
{
    private String mode;
    private String path;
    private String localUrl;
    private String azureUrl;
}
