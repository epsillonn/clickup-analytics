package org.example.repository;

import java.util.List;
import org.example.entity.WebhookEventLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookEventLogRepository extends JpaRepository<WebhookEventLogs, Long> {
    List<WebhookEventLogs> findByStatus(String status);

    List<WebhookEventLogs> findByWorkspaceId(String workspaceId);

    List<WebhookEventLogs> findByWebhookId(String webhookId);
}
