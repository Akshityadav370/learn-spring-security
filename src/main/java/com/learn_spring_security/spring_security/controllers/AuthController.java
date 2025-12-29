package com.learn_spring_security.spring_security.controllers;


import com.learn_spring_security.spring_security.dto.LoginDto;
import com.learn_spring_security.spring_security.dto.SignupDto;
import com.learn_spring_security.spring_security.dto.UserDto;
import com.learn_spring_security.spring_security.entity.LoginResponseDto;
import com.learn_spring_security.spring_security.exceptions.ResourceNotFoundException;
import com.learn_spring_security.spring_security.services.AuthService;
import com.learn_spring_security.spring_security.services.SessionEntityService;
import com.learn_spring_security.spring_security.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final SessionEntityService sessionEntityService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupDto signUpDto) {
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest request,
                                                  HttpServletResponse response) {
        LoginResponseDto loginResponseDto = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refreshAccess")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getRefreshTokenFromReqCookies(request.getCookies());

        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);

        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getRefreshTokenFromReqCookies(request.getCookies());

        authService.logout(refreshToken);

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully!");
    }

    public final String getRefreshTokenFromReqCookies(Cookie[] cookies) {
        Cookie refreshTokenCookie = null;

        if (cookies != null) {
            refreshTokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> "refreshToken".equals(cookie.getName()))
                    .findFirst()
                    .orElse(null);
        }

        if (refreshTokenCookie == null) {
            throw new ResourceNotFoundException("Refresh Token not found!");
        }

        return refreshTokenCookie.getValue();
    }
}
