package com.example.auth_service.dto.user;

public record ValidateSecurityCodeDTO(
        String email,
        String securityCode
) {
}
