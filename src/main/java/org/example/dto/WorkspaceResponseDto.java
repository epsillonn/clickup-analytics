package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record WorkspaceResponseDto(@JsonProperty("teams") List<WorkspaceDto> teams) {
    public record WorkspaceDto(String id, String name, List<WorkspaceMemberDto> members) {}

    public record WorkspaceMemberDto(
            UserDto user, @JsonProperty("invited_by") UserDto invitedBy) {}
}
