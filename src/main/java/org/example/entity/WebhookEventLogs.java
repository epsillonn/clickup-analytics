package org.example.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Text;

import java.sql.Timestamp;

@Entity
@Table(name="webhook_event_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebhookEventLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="webhook_id")
    private Webhook webhook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "status")
    private String status;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "retry_count")
    private Integer retryCount;

    @Column(name = "received_at")
    private Timestamp receivedAt;

    @Column(name = "processed_at")
    private Timestamp processedAt;

}
