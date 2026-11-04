package org.example.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "hierarchy_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HierarchyEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_type", nullable = false, length = 50)
    private String entityType;


    @Column(name = "entity_id", nullable = false, length = 100)
    private String entityId;


    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;


    @Column(name = "field_changed", length = 100)
    private String fieldChanged;


    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;

    @Column(name = "changed_by_user_id")
    private Long changedByUserId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "delivery_id", length = 100)
    private String deliveryId;

    @Column(name = "trigger_source", length = 50)
    @Builder.Default
    private String triggerSource = "WEBHOOK";

}
