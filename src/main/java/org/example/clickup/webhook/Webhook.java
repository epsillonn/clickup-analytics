package org.example.clickup.webhook;

import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/** A webhook registration. {@code secret} is the HMAC-SHA256 key for the {@code X-Signature} header of each delivery. */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Webhook(
        String id,
        Long userid,
        Long teamId,
        String endpoint,
        String clientId,
        List<WebhookEventType> events,
        String taskId,
        String listId,
        String folderId,
        String spaceId,
        Health health,
        String secret) {

    /** {@code status} is {@code active}, {@code failing} or {@code suspended}. */
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Health(String status, Integer failCount) {}
}
