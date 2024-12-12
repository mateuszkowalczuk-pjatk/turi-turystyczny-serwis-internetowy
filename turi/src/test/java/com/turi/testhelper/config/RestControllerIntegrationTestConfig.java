package com.turi.testhelper.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class RestControllerIntegrationTestConfig
{
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    TestRestTemplate restTemplateTest()
    {
        return new TestRestTemplate();
    }
}
