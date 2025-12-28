package com.learn_spring_security.spring_security.services;

import com.learn_spring_security.spring_security.dto.LoginDto;
import com.learn_spring_security.spring_security.entity.SessionEntity;
import com.learn_spring_security.spring_security.entity.UserEntity;
import com.learn_spring_security.spring_security.repository.SessionEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SessionEntityRepository sessionEntityRepository;

    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        SessionEntity session = sessionEntityRepository
                .findByUserId(user.getId())
                .orElse(new SessionEntity());

        session.setUserId(user.getId());
        session.setToken(token);
        sessionEntityRepository.save(session);

        return token;
    }
}
