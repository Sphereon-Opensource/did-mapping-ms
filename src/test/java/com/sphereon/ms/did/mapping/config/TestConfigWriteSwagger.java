package com.sphereon.ms.did.mapping.config;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.SimpleThreadScope;

@Configuration
@ComponentScan(basePackages = {"com.sphereon"})
//@Profile("write-swagger")
public class TestConfigWriteSwagger {

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        customScopeConfigurer.addScope("session", new SimpleThreadScope());
        return customScopeConfigurer;
    }
}
