package com.vetlife.api.modules.auth.dto;

import com.vetlife.api.modules.auth.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthDTOs {
    public record LoginRequest(
        @NotBlank String login, 
        @NotBlank String password
    ) {}
    
    public record RegisterRequest(
        @NotBlank String login, 
        @NotBlank String password,
        @NotNull UserRole role
    ) {}
    
    public record LoginResponse(
        String token,
        String login,
        UserRole role
    ) {}
    
    public record UserResponse(
        Long id,
        String login,
        UserRole role,
        Boolean active
    ) {}
}