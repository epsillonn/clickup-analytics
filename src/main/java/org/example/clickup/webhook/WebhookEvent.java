package org.example.clickup.webhook;

import java.util.List;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * The body ClickUp POSTs for a webhook subscription. Only the ID of the resource the event is about is set, and
 * {@code historyItems} is absent for create and delete events of Spaces, Folders, Lists and Goals. ClickUp asks
 * receivers to deduplicate on {@code webhookId + ":" + historyItem.id}.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record WebhookEvent(
        WebhookEventType event,
        String webhookId,
        String taskId,
        String listId,
        String folderId,
        String spaceId,
        String goalId,
        String keyResultId,
        List<HistoryItem> historyItems,
        TimeTrackingData data) {

    /** Sent only with {@code taskTimeTrackedUpdated}. */
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record TimeTrackingData(String description, String intervalId) {}
}
