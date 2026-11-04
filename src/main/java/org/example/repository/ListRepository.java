package org.example.repository;

import java.util.List;
import org.example.entity.Lists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<Lists, String> {
    List<Lists> findBySpaceId(String spaceId);

    List<Lists> findByFolderId(String folderId);
}
