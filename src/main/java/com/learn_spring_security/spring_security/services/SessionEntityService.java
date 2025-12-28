package com.learn_spring_security.spring_security.services;

import com.learn_spring_security.spring_security.entity.SessionEntity;
import com.learn_spring_security.spring_security.exceptions.ResourceNotFoundException;
import com.learn_spring_security.spring_security.repository.SessionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionEntityService {
    private final SessionEntityRepository sessionEntityRepository;

    public final Optional<SessionEntity> getSessionByUserId(Long userId) {
        return sessionEntityRepository.getByUserId(userId);
    }
}
