package org.example.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
