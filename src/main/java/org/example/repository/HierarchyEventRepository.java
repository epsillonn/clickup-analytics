package org.example.repository;

import org.example.entity.HierarchyEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HierarchyEventRepository extends JpaRepository<HierarchyEvent, Long> {
    List<HierarchyEvent> findByEntityTypeAndEntityId(String entityType, String entityId);
}
