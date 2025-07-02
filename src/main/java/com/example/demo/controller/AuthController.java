package com.example.demo.controller;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// This controller handles authentication-related endpoints such as login.
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // Service for user-related operations, including authentication logic.
    private final UserService userService;

    /**
     * Authenticates a user with the provided login credentials.
     * @param loginDTO The login request containing username and password.
     * @return A response containing authentication details (e.g., JWT token).
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        AuthResponseDTO response = userService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}
