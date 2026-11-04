package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HierarchyEventResponseDto {
    private Long id;
    private String entityType;
    private String entityId;
    private String eventType;
    private String fieldChanged;
    private String oldValue;
    private String newValue;
    private Long changedByUserId;
    private String deliveryId;
    private String triggerSource;
    private OffsetDateTime createdAt;
}
