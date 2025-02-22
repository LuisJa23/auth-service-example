package com.example.auth_service.controller;


import com.example.auth_service.dto.VerificationCodeRecoveryPasswordDTO;
import com.example.auth_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/password-recovery")
    public ResponseEntity<VerificationCodeRecoveryPasswordDTO> sendRecoveryCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        var verificationCodeRecoveryPasswordDTO = emailService.sendRecoveryCode(email);
        return ResponseEntity.ok(verificationCodeRecoveryPasswordDTO);

    }

}
