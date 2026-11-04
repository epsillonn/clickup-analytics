package org.example.repository;

import org.example.entity.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebhookRepository extends JpaRepository<Webhook, String> {

}
