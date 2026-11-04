package org.example.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class TaskEventResponseDto {
        private Long id;
        private String taskId;
        private String eventType;
        private String fromStatus;
        private String toStatus;
        private String fromPriority;
        private String toPriority;
        private Long fromAssignee;
        private Long toAssignee;
        private Long changedByUserId;
        private String timestamp;
        private String triggerSource;
}
