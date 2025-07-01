package com.example.demo.services;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.AuthResponseDTO;

public interface UserService {
    AuthResponseDTO login(LoginRequestDTO loginDTO);
}
