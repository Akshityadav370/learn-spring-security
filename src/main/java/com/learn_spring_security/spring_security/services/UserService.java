package com.learn_spring_security.spring_security.services;

import com.learn_spring_security.spring_security.dto.SignupDto;
import com.learn_spring_security.spring_security.dto.UserDto;
import com.learn_spring_security.spring_security.entity.UserEntity;
import com.learn_spring_security.spring_security.exceptions.ResourceNotFoundException;
import com.learn_spring_security.spring_security.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserEntityRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User with email "+ username +" not found"));
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id "+ userId +
                " not found"));
    }

    public UserDto signUp(SignupDto signUpDto) {
        return null;
    }

    public UserEntity getUsrByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserEntity save(UserEntity newUser) {
        return userRepository.save(newUser);
    }
}
