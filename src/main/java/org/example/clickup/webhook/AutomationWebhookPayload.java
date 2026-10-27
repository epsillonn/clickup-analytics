package org.example.clickup.webhook;

import java.time.Instant;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * The body an Automation's "Call webhook" action POSTs. It is unsigned and carries no {@code event}. The current action
 * sends {@code autoId}; the legacy action sends {@code id} instead. {@code payload} is the task, in a shape that
 * differs between the two actions.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AutomationWebhookPayload(String autoId, String id, String triggerId, Instant date, JsonNode payload) {}
