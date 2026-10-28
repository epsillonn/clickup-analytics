package org.example.clickup.webhook;

import org.example.clickup.model.CustomField;
import org.example.clickup.model.User;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * One change described by a webhook delivery. {@code date} is Unix epoch milliseconds.
 *
 * <p>{@code before} and {@code after} are raw JSON because their shape depends on {@code field}: a {@link
 * org.example.clickup.model.Status} for {@code status}, a {@link org.example.clickup.model.Priority} for {@code
 * priority}, a {@link User} for {@code assignee_add} and {@code assignee_rem}, a list of {@link
 * org.example.clickup.model.Tag} for {@code tag}, a {@link MovedLocation} for {@code section_moved}, a {@link
 * TrackedInterval} for {@code time_spent}, and epoch milliseconds or plain text for date, estimate, name and content
 * changes.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HistoryItem(
        String id,
        Integer type,
        Long date,
        String field,
        String parentId,
        JsonNode data,
        JsonNode source,
        User user,
        JsonNode before,
        JsonNode after,
        CustomField customField,
        WebhookComment comment) {}
