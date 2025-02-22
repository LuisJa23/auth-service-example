package com.example.auth_service.dto;

public record VerificationCodeRecoveryPasswordDTO(String verificationCode) {
    public VerificationCodeRecoveryPasswordDTO(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}