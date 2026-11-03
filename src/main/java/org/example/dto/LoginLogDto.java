package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginLogDto(
        Long id,
        @JsonProperty("user_id") Long userId,
        @JsonProperty("ip_address") String ipAddress,
        String browser,
        @JsonProperty("operating_system") String operatingSystem,
        @JsonProperty("device_model") String deviceModel,
        @JsonProperty("logged_in_at") Long loggedInAt
) {
}
