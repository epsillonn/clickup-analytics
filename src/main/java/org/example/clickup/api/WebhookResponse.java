package org.example.clickup.api;

import org.example.clickup.webhook.Webhook;

public record WebhookResponse(String id, Webhook webhook) {}
