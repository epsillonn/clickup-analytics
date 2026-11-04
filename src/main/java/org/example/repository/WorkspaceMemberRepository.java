package org.example.repository;

import java.util.List;
import java.util.Optional;
import org.example.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {
    List<WorkspaceMember> findByWorkspaceId(String workspaceId);

    List<WorkspaceMember> findByUserId(Long userId);

    Optional<WorkspaceMember> findByWorkspaceIdAndUserId(String workspaceId, Long userId);
}
