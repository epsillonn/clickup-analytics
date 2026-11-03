package org.example.repository;

import org.example.entity.Webhook;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebhookRepository  extends JpaRepository<Webhook,String> {
    List<Webhook> findByWorkspaceId(String workspaceId);
    Optional<Webhook> findByIdAndStatus(String id, String status);
}
