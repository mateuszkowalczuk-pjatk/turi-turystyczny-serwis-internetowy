package com.turi.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class JacksonConfig
{
    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper()
                .registerModules(new JavaTimeModule(), new Jdk8Module())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
