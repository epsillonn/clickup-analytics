package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.example.entity.User;

public record AuthResponseDto(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") long expiresIn,
        User user,
        List<WorkspaceInfo> workspaces) {
    public record WorkspaceInfo(String id, String name) {}

    public static AuthResponseDto of(String accessToken, long expiresIn, User user, List<WorkspaceInfo> workspaces) {
        return new AuthResponseDto(accessToken, "Bearer", expiresIn, user, workspaces);
    }
}
