package com.example.demo.services;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.AuthResponseDTO;

// Service interface for user authentication and related operations.
public interface UserService {
    /**
     * Authenticates a user with the provided login credentials.
     * @param loginDTO The login request containing username and password.
     * @return A response containing authentication details (e.g., JWT token).
     */
    AuthResponseDTO login(LoginRequestDTO loginDTO);
}
