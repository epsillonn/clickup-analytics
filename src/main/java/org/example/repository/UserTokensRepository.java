package org.example.repository;

import org.example.entity.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {
    Optional<UserTokens> findByUserIdAndWorkspaceId(Long userId, String workspaceId);

    @Transactional
    void deleteByUserId(Long userId);
}
