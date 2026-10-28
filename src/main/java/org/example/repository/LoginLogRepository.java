package org.example.repository;

import org.example.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginLogRepository extends JpaRepository<LoginLog,Long> {
    List<LoginLog> findByUserIdOrderByLoggedInAtDesc(Long userId);
}
