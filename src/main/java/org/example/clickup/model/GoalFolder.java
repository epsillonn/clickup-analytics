package org.example.clickup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoalFolder(
        String id,
        String name,
        String teamId,
        @JsonProperty("private") Boolean isPrivate,
        Long dateCreated,
        Long creator,
        Integer goalCount,
        List<GoalMember> members,
        List<Goal> goals) {}
