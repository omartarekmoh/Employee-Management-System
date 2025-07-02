package com.example.demo.services.impl;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Implementation of UserService for handling user authentication logic.
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Repository for user data access.
    private final UserRepository userRepository;
    // Password encoder for verifying user credentials.
    private final PasswordEncoder passwordEncoder;
    // Utility for generating JWT tokens.
    private final JwtUtils jwtUtils;

    /**
     * Authenticates a user and generates a JWT token if credentials are valid.
     */
    @Override
    public AuthResponseDTO login(LoginRequestDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new EntityNotFoundException("Invalid email or password");
        }
        String role = String.valueOf(user.getRole());
        String token = jwtUtils.generateToken(user.getEmail(), role);
        return new AuthResponseDTO(token);
    }
}
