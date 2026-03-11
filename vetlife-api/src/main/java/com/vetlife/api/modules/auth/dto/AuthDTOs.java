package com.vetlife.api.modules.auth.dto;
import jakarta.validation.constraints.NotBlank;
public class AuthDTOs {
    public record LoginRequest(@NotBlank String login, @NotBlank String password) {}
    public record RegisterRequest(@NotBlank String login, @NotBlank String password) {}
    public record LoginResponse(String token) {}
}