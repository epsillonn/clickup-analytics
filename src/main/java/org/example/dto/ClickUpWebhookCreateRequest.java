package org.example.dto;

import java.util.List;
import org.example.enums.WebhookSubscribedEvent;

public record ClickUpWebhookCreateRequest(String endpoint, List<String> events) {
    public static ClickUpWebhookCreateRequest defaultSubscription(String endpointUrl) {
        List<String> subscribedEvents = List.of(
                WebhookSubscribedEvent.TASK_CREATED.getEventName(),
                WebhookSubscribedEvent.TASK_UPDATED.getEventName(),
                WebhookSubscribedEvent.TASK_DELETED.getEventName(),
                WebhookSubscribedEvent.LIST_CREATED.getEventName(),
                WebhookSubscribedEvent.LIST_UPDATED.getEventName(),
                WebhookSubscribedEvent.LIST_DELETED.getEventName(),
                WebhookSubscribedEvent.FOLDER_CREATED.getEventName(),
                WebhookSubscribedEvent.FOLDER_UPDATED.getEventName(),
                WebhookSubscribedEvent.FOLDER_DELETED.getEventName(),
                WebhookSubscribedEvent.SPACE_CREATED.getEventName(),
                WebhookSubscribedEvent.SPACE_UPDATED.getEventName(),
                WebhookSubscribedEvent.SPACE_CREATED.getEventName(),
                WebhookSubscribedEvent.SPACE_UPDATED.getEventName(),
                WebhookSubscribedEvent.SPACE_DELETED.getEventName());
        return new ClickUpWebhookCreateRequest(endpointUrl, subscribedEvents);
    }
}
