package org.example.repository;

import org.example.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,String> {
    List<Task> findByListId(String listId);
    List<Task> findByWorkspaceId(String workspaceId);
    List<Task> findByParentId(String parentId);
    List<Task> findByStatusName(String statusName);
    List<Task> findByCreatorId(Long creatorId);
}
