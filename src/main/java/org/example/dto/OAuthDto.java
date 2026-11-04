package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthDto() {
    public record Request(
            @JsonProperty("client_id") String clientId,
            @JsonProperty("client_secret") String clientSecret,
            String code) {}

    public record Response(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("token_type") String tokenType) {}
}
