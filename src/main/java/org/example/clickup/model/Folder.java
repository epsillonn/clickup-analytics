package org.example.clickup.model;

import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Folder(
        String id,
        String name,
        Integer orderindex,
        Boolean overrideStatuses,
        Boolean hidden,
        HierarchyRef space,
        Integer taskCount,
        Boolean archived,
        List<Status> statuses,
        List<ClickUpList> lists,
        String parentFolder,
        List<Folder> folders,
        String permissionLevel) {}
