package org.example.clickup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoalMember(
        Long id,
        String username,
        String email,
        String color,
        String initials,
        @JsonProperty("profilePicture") String profilePicture,
        String permissionLevel,
        @JsonProperty("isCreator") Boolean isCreator) {}
