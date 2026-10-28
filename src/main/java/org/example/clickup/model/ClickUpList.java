package org.example.clickup.model;

import java.util.List;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ClickUpList(
        String id,
        String name,
        Integer orderindex,
        String content,
        ListStatus status,
        ListPriority priority,
        JsonNode assignee,
        Integer taskCount,
        Long dueDate,
        Boolean dueDateTime,
        Long startDate,
        Boolean startDateTime,
        HierarchyRef folder,
        HierarchyRef space,
        String inboundAddress,
        Boolean archived,
        Boolean overrideStatuses,
        List<Status> statuses,
        String permissionLevel) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record ListStatus(String status, String color, Boolean hideLabel) {}

    public record ListPriority(String priority, String color) {}
}
