package com.example.auth_service.dto;


import com.example.auth_service.model.Role;
import com.example.auth_service.model.User;

import java.util.List;


public record JWTAndLoginAuthenticatedDTO(
        Long id,
        String username,
        List<String> roles,
        String jwtToken

) {
    public JWTAndLoginAuthenticatedDTO(User user, String jwtToken)
    {
        this(user.getId(), user.getUsername(), user.getRoles().stream().map(Role::getName).toList(), jwtToken);
    }
}
