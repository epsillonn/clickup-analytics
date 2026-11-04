package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record FolderResponseDto(@JsonProperty("folders") List<FolderDto> folders) {
    public record FolderDto(
            String id,
            String name,
            Boolean hidden,
            @JsonProperty("override_statuses") Boolean overrideStatuses,
            Boolean archived,
            List<ListDto> lists) {}

    public record ListDto(
            String id,
            String name,
            @JsonProperty("task_count") Integer taskCount,
            @JsonProperty("override_statuses") Boolean overrideStatuses,
            List<StatusDto> statuses) {}
}
