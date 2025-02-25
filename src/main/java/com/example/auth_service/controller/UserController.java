package com.example.auth_service.controller;

import com.example.auth_service.dto.ChangePasswordDTO;
import com.example.auth_service.dto.user.UserRegisterDTO;
import com.example.auth_service.dto.user.UserRegisterResponseDTO;
import com.example.auth_service.dto.user.ValidateSecurityCodeDTO;
import com.example.auth_service.service.UserService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public ResponseEntity<UserRegisterResponseDTO> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {

        return ResponseEntity.ok(userService.registerUser(userRegisterDTO));
    }

    @PostMapping("/validate-code")
    @Transactional
    public ResponseEntity validateCode(@RequestBody @Valid ValidateSecurityCodeDTO changePasswordDTO) {

        if(userService.isValidateCode(changePasswordDTO)){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/change-password")
    @Transactional
    public ResponseEntity changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) {

        userService.changePassword(changePasswordDTO);
        return ResponseEntity.ok().build();
    }



}
