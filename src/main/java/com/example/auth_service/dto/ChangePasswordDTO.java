package com.example.auth_service.dto;

public record ChangePasswordDTO(
        String email,
        String password,
        String securityCode
) {
}
