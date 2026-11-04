package org.example.repository;

import java.util.List;
import org.example.entity.TaskAssignee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAssigneeRepository extends JpaRepository<TaskAssignee, Long> {
    List<TaskAssignee> findByTaskId(String taskId);

    List<TaskAssignee> findByUserId(Long userId);
}
