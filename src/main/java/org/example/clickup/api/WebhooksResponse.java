package org.example.clickup.api;

import java.util.List;
import org.example.clickup.webhook.Webhook;

public record WebhooksResponse(List<Webhook> webhooks) {}
