package org.example.dto;

import java.sql.Timestamp;

public class WebhookResponseDto {
    private String id;
    private String workspaceId;
    private String endpointUrl;
    private Long userId;
    private String secret;
    private String status;
    private Integer failCount;
    private Long createdByUserId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
