package com.example.auth_service.dto.user;

import com.example.auth_service.model.User;

public record UserRegisterResponseDTO (
        String username,
        String email
){

    public UserRegisterResponseDTO(User user){
        this(user.getUsername(), user.getEmail());
    }
}
