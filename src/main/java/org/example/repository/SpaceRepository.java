package org.example.repository;

import java.util.List;
import org.example.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, String> {
    List<Space> findByWorkspaceId(String workspaceId);
}
