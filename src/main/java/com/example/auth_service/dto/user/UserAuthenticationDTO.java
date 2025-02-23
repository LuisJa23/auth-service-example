package com.example.auth_service.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationDTO(
        @Email
        @JsonProperty("email")
        String email,

        @NotBlank
        @JsonProperty("password")
        String password

) {

}
