package com.learn_spring_security.spring_security.services;

import com.learn_spring_security.spring_security.dto.LoginDto;
import com.learn_spring_security.spring_security.entity.LoginResponseDto;
import com.learn_spring_security.spring_security.entity.SessionEntity;
import com.learn_spring_security.spring_security.entity.UserEntity;
import com.learn_spring_security.spring_security.exceptions.UnauthorizedException;
import com.learn_spring_security.spring_security.repository.SessionEntityRepository;
import com.learn_spring_security.spring_security.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionEntityRepository sessionEntityRepository;
    private final SessionRepository sessionRepository;
    private final SessionService sessionService;

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user, refreshToken);

        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        UserEntity user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User not authenticated");
        }

        UserEntity user = (UserEntity) authentication.getPrincipal();

        if (user != null) {
            sessionRepository.deleteByUser(user);
        }
    }
}
