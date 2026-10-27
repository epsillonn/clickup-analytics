package org.example.clickup.webhook;

import java.util.List;
import org.example.clickup.model.CommentSegment;
import org.example.clickup.model.User;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record WebhookComment(
        String id,
        Long date,
        String parent,
        Integer type,
        List<CommentSegment> comment,
        String textContent,
        User user,
        Integer threadedReplies,
        Integer threadedUnresolvedCount,
        List<User> threadFollowers,
        List<JsonNode> reactions) {}
