package com.salesapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for setting up application-wide beans.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates and configures a {@link RestTemplate} bean.
     * 
     * @return A new instance of {@link RestTemplate}.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
