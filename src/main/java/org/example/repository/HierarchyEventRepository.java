package org.example.repository;

import java.util.List;
import org.example.entity.HierarchyEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HierarchyEventRepository extends JpaRepository<HierarchyEvent, Long> {
    List<HierarchyEvent> findByEntityTypeAndEntityId(String entityType, String entityId);
}
