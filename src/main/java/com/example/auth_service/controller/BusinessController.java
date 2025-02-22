package com.example.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @GetMapping("/admin")
    public ResponseEntity<String> getAdminPermissions() {
        return ResponseEntity.ok("Tienes permisos de administrador");
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUserPermissions() {
        return ResponseEntity.ok("Tienes permisos de usuario");
    }


}
