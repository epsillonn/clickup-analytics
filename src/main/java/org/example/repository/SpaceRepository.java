package org.example.repository;

import org.example.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceRepository extends JpaRepository<Space,String> {
    List<Space> findByWorkspaceId(String workspaceId);
}
