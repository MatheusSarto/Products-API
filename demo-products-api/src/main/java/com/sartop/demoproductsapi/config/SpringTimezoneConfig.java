package com.sartop.demoproductsapi.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class SpringTimezoneConfig
{
    @PostConstruct
    public void TimezoneConfig()
    {
        // TODO CHANGE TIMEZONE ID TO BE CONFIGURATION VARIABLE
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
