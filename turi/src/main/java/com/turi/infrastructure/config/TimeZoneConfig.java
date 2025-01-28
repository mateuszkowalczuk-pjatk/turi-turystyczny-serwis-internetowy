package com.turi.infrastructure.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimeZoneConfig
{
    @PostConstruct
    public void setDefaultTimeZone()
    {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"));
    }
}
