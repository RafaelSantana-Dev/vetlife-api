package com.vetlife.api.modules.auth;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    VET("ROLE_VET"),
    RECEPTIONIST("ROLE_RECEPTIONIST");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
