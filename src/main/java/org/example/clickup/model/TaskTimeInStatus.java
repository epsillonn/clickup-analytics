package org.example.clickup.model;

import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TaskTimeInStatus(StatusTime currentStatus, List<StatusTime> statusHistory) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record StatusTime(String status, String color, String type, Integer orderindex, TotalTime totalTime) {}

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record TotalTime(Long byMinute, Long since) {}
}
