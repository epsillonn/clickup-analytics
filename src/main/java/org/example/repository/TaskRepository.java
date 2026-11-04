package org.example.repository;

import java.util.List;
import org.example.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByListId(String listId);

    List<Task> findByWorkspaceId(String workspaceId);

    List<Task> findByParentId(String parentId);

    List<Task> findByStatusName(String statusName);

    List<Task> findByCreatorId(Long creatorId);
}
