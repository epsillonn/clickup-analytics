package org.example.clickup.model;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Attachment(
        String id,
        Long date,
        String title,
        Integer type,
        Integer source,
        Integer version,
        String extension,
        String mimetype,
        Boolean isFolder,
        Boolean hidden,
        Boolean deleted,
        String parentId,
        Long size,
        Integer totalComments,
        Integer resolvedComments,
        User user,
        String url) {}
