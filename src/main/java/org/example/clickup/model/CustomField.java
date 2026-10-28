package org.example.clickup.model;

import java.util.List;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * A Custom Field definition, and on tasks also its value. {@code typeConfig} and {@code value} are kept as raw JSON
 * because their shape depends on {@code type}, and ClickUp does not normalize values.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CustomField(
        String id,
        String name,
        String type,
        JsonNode typeConfig,
        Long dateCreated,
        Boolean hideFromGuests,
        Boolean required,
        JsonNode value,
        String valueRichtext,
        String valueMarkdown,
        List<AppliedObject> appliedObjects) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record AppliedObject(Integer objectType, Long objectId) {}
}
