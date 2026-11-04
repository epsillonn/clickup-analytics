package org.example.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(ClickUpProperties.class)
public class RestClientConfig {

    private final ClickUpProperties clickUpProperties;

    public RestClientConfig(ClickUpProperties clickUpProperties) {
        this.clickUpProperties = clickUpProperties;
    }

    @Bean
    public RestClient clickUpRestClient() {
        return RestClient.builder()
                .baseUrl(clickUpProperties.api().baseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
