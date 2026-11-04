package org.example.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="task_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "from_status", length = 100)
    private String fromStatus;

    @Column(name = "to_status", length = 100)
    private String toStatus;

    @Column(name = "from_priority", length = 50)
    private String fromPriority;

    @Column(name = "to_priority", length = 50)
    private String toPriority;

    @Column(name = "from_assignee")
    private Long fromAssignee;

    @Column(name = "to_assignee")
    private Long toAssignee;

    @Column(name = "changed_by_user_id")
    private Long changedByUserId;

    @Column(name = "timestamp", length = 100)
    private String timestamp; // ClickUp epoch millis string

    // İzlenebilirlik Alanları
    @Column(name = "delivery_id", length = 100)
    private String deliveryId;

    @Column(name = "history_item_id", length = 100)
    private String historyItemId;

    @Column(name = "trigger_source", length = 50)
    @Builder.Default
    private String triggerSource = "WEBHOOK";

}
