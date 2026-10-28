package org.example.clickup.model;

import java.util.List;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Checklist(
        String id,
        String taskId,
        String name,
        Long dateCreated,
        Integer orderindex,
        Long creator,
        Integer resolved,
        Integer unresolved,
        List<Item> items) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Item(
            String id,
            String name,
            Integer orderindex,
            JsonNode assignee,
            Boolean resolved,
            String parent,
            Long dateCreated,
            List<Item> children) {}
}
