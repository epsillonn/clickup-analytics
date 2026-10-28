package org.example.repository;

import org.example.entity.WebhookEventLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebhookEventLogRepository extends JpaRepository<WebhookEventLogs,Long> {
    List<WebhookEventLogs> findByStatus(String status);
    List<WebhookEventLogs> findByWorkspaceId(String workspaceId);
    List<WebhookEventLogs> findByWebhookId(String webhookId);
}
