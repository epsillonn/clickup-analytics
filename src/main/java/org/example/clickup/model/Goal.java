package org.example.clickup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Goal(
        String id,
        String prettyId,
        String name,
        String teamId,
        Long creator,
        JsonNode owner,
        String color,
        Long dateCreated,
        Long startDate,
        Long dueDate,
        String description,
        @JsonProperty("private") Boolean isPrivate,
        Boolean archived,
        Boolean multipleOwners,
        Long dateUpdated,
        Long lastUpdate,
        String folderId,
        Boolean pinned,
        List<User> owners,
        Integer keyResultCount,
        List<GoalMember> members,
        Integer percentCompleted,
        List<JsonNode> keyResults,
        String prettyUrl) {}
