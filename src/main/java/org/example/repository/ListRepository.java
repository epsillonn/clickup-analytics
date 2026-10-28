package org.example.repository;

import org.example.entity.Lists;
import org.example.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListRepository extends JpaRepository<Lists,String> {
    List<Lists> findBySpaceId(String spaceId);
    List<Lists> findByFolderId(String folderId);
}
