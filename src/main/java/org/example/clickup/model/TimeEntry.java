package org.example.clickup.model;

import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * A time entry. {@code start}, {@code end} and {@code at} are Unix epoch milliseconds and {@code duration} is in
 * milliseconds; a running timer has no {@code end} and a negative {@code duration}.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TimeEntry(
        String id,
        TaskRef task,
        String wid,
        User user,
        Boolean billable,
        Long start,
        Long end,
        Long duration,
        String description,
        List<Tag> tags,
        String source,
        Long at,
        TaskLocation taskLocation,
        List<Tag> taskTags,
        String taskUrl) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record TaskRef(String id, String customId, String name, Status status, Integer customType) {}

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record TaskLocation(
            String listId, String folderId, String spaceId, String listName, String folderName, String spaceName) {}
}
