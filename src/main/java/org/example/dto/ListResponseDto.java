package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ListResponseDto(@JsonProperty("lists") List<ListDto> lists) {
    public record ListDto(
            String id,
            String name,
            @JsonProperty("task_count") Integer taskCount,
            @JsonProperty("due_date") String dueDate,
            @JsonProperty("start_date") String startDate,
            Boolean archived,
            @JsonProperty("override_statuses") Boolean overrideStatuses,
            List<StatusDto> statuses,
            FolderRefDto folder) {}

    public record FolderRefDto(String id, String name, Boolean hidden) {}
}
