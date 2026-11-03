package org.example.repository;

import org.example.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder,String> {
    List<Folder> findBySpaceId(String spaceId);
}
