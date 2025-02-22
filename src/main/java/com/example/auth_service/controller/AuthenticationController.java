package com.example.auth_service.controller;


import com.example.auth_service.dto.JWTAndLoginAuthenticatedDTO;
import com.example.auth_service.dto.UserAuthenticationDTO;
import com.example.auth_service.model.User;
import com.example.auth_service.service.UserService;
import com.example.auth_service.service.authentication.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")

public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;



    @PostMapping
    @Transactional
    public ResponseEntity authenticateLogin(@RequestBody @Valid UserAuthenticationDTO userAuthenticationData) {

        var user = userService.getUserByEmail(userAuthenticationData.email());

        Authentication authToken = new UsernamePasswordAuthenticationToken(userAuthenticationData.email(),
                userAuthenticationData.password());
        var authenticatedUser = authenticationManager.authenticate(authToken);
        String JWTToken = tokenService.generateToken((User) authenticatedUser.getPrincipal(), (User) user);

        return ResponseEntity.ok(new JWTAndLoginAuthenticatedDTO((User) user, JWTToken));
    }




}
