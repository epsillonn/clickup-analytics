package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookIngestionPayload {
    private String rawJsonPayload;
    private String deliveryId;
    private String webhookId;
}
