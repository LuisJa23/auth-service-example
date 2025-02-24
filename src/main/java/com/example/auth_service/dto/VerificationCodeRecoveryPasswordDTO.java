package com.example.auth_service.dto;

public record VerificationCodeRecoveryPasswordDTO(

        String email,
        String verificationCode
) {
    public VerificationCodeRecoveryPasswordDTO(String email,String verificationCode) {
        this.verificationCode = verificationCode;
        this.email = email;
    }
}