package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record TaskResponseDto(@JsonProperty("tasks") List<TaskDto> tasks) {
    public record TaskDto(
            String id,
            String name,
            String description,
            StatusDto status,
            @JsonProperty("date_created") String dateCreated,
            @JsonProperty("date_updated") String dateUpdated,
            @JsonProperty("date_closed") String dateClosed,
            @JsonProperty("due_date") String dueDate,
            @JsonProperty("start_date") String startDate,
            Boolean archived,
            @JsonProperty("time_estimate") String timeEstimate,
            @JsonProperty("time_spent") String timeSpent,
            String parent,
            @JsonProperty("top_level_parent") String topLevelParent,
            PriorityDto priority,
            UserDto creator,
            List<UserDto> assignees,
            List<DependencyDto> dependencies,
            List<LocationDto> locations,
            String url) {}

    public record PriorityDto(String id, String priority) {}

    public record DependencyDto(
            @JsonProperty("task_id") String taskId,
            @JsonProperty("depends_on") String dependsOn,
            @JsonProperty("user_id") String userId,
            @JsonProperty("date_created") Long dateCreated) {}

    public record LocationDto(String id, String name) {}
}
