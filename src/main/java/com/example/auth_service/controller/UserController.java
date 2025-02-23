package com.example.auth_service.controller;

import com.example.auth_service.dto.user.UserRegisterDTO;
import com.example.auth_service.dto.user.UserRegisterResponseDTO;
import com.example.auth_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {

        return ResponseEntity.ok(userService.registerUser(userRegisterDTO));
    }

}
