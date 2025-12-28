package com.learn_spring_security.spring_security.repository;

import com.learn_spring_security.spring_security.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionEntityRepository extends JpaRepository<SessionEntity, Long> {

    boolean existsByUserIdAndToken(Long userId, String token);
    Optional<SessionEntity> getByUserId(Long userId);
    Optional<SessionEntity> findByUserId(Long userId);
}