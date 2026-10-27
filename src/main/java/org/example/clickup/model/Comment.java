package org.example.clickup.model;

import java.util.List;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Comment(
        String id,
        List<CommentSegment> comment,
        String commentText,
        User user,
        Boolean resolved,
        User assignee,
        User assignedBy,
        List<JsonNode> reactions,
        Long date,
        Integer replyCount) {}
