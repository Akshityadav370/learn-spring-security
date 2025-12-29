package com.learn_spring_security.spring_security.repository;

import com.learn_spring_security.spring_security.entity.Session;
import com.learn_spring_security.spring_security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUser(UserEntity user);

    Optional<Session> findByRefreshToken(String refreshToken);

    void deleteByUser(UserEntity user);

    void deleteByRefreshToken(String refreshToken);
}