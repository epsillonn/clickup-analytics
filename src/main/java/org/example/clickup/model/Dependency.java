package org.example.clickup.model;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Dependency(
        String taskId,
        String dependsOn,
        Integer type,
        Long dateCreated,
        String userid,
        String workspaceId,
        String chainId) {}
