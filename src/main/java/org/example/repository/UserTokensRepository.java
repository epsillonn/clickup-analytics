package org.example.repository;

import org.example.entity.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokensRepository extends JpaRepository<UserTokens, Long> {
    Optional<UserTokens> findByUserId(Long userId);
    Optional<UserTokens> findByAccessToken(String accessToken);
}
