package com.turi.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer
{
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry)
    {
        if (!registry.hasMappingForPattern("/uploads/**"))
        {
            registry
                    .addResourceHandler("/uploads/**")
                    .addResourceLocations("file:./uploads/");
        }
    }
}
