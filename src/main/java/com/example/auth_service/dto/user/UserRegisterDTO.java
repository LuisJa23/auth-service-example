package com.example.auth_service.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO (
        @NotBlank
        String username,
        @Email
        String email,
        @Size(min = 8)
        String password

) {
}
