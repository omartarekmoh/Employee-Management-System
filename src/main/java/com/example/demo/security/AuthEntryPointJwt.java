package com.example.demo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Entry point for handling unauthorized access (401 errors) in the security framework.
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    /**
     * Handles unauthorized access by sending a 401 response with a JSON error message.
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.getWriter().write("{ \"error\": \"Unauthorized access - Please login with a valid token\" }");
    }
}
