package com.salesapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Main application class for the Sales Microservice.
 * This class bootstraps the Spring Boot application.
 */
@SpringBootApplication
@EntityScan({"com.salesapplication.model", "com.vehicleinventorysystem.model"})
public class SalesmicroserviceApplication {

    /**
     * The main method that serves as the entry point for the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(SalesmicroserviceApplication.class, args);
    }
}
