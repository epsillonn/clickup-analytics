package org.example.repository;

import org.example.entity.TaskAssignee;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAssigneeRepository extends JpaRepository<TaskAssignee,Long> {
    List<TaskAssignee> findByTaskId(String taskId);
    List<TaskAssignee> findByUserId(Long userId);
}
