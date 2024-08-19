package com.turi.testhelper.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RestControllerIntegrationTestConfig
{
    @Bean
    TestRestTemplate restTemplate()
    {
        return new TestRestTemplate();
    }
}
