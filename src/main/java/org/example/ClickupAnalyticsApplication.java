package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
public class ClickupAnalyticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClickupAnalyticsApplication.class, args);
    }
}