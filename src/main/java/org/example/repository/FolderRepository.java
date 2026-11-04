package org.example.repository;

import java.util.List;
import org.example.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, String> {
    List<Folder> findBySpaceId(String spaceId);
}
