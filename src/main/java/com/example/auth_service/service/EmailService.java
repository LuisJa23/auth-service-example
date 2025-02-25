package com.example.auth_service.service;


import com.example.auth_service.dto.VerificationCodeRecoveryPasswordDTO;
import com.example.auth_service.infra.security.config.HmacEncryption;
import com.example.auth_service.model.SecurityCode;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.SecurityCodeRepository;
import com.example.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityCodeRepository securityCodeRepository;

    @Autowired
    private JavaMailSender mailSender;

    public VerificationCodeRecoveryPasswordDTO sendRecoveryCode(String toEmail) {
        if (!userRepository.existsByEmail(toEmail)) {
            throw new IllegalArgumentException("No existe un usuario con ese correo electrónico.");
        }

        String recoveryCode = String.format("%06d", new Random().nextInt(999999));




        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("🔐 Recuperación de contraseña");
        message.setText(
                "Hola,\n\n" +
                        "Hemos recibido una solicitud para restablecer tu contraseña. " +
                        "Utiliza el siguiente código para continuar con el proceso de recuperación:\n\n" +
                        "🔑 Código de recuperación: *" + recoveryCode + "*\n\n" +
                        "Si no solicitaste este cambio, puedes ignorar este mensaje.\n\n" +
                        "Atentamente,\n" +
                        "El equipo de soporte");



        mailSender.send(message);
        String code = HmacEncryption.encryptKey(recoveryCode);
        User user = (User) userRepository.findByEmail(toEmail);

        if(user.getSecurityCode() == null){
            SecurityCode securityCode = new SecurityCode(code, user);
            user.setSecurityCode(securityCode);
            securityCodeRepository.save(securityCode);
            userRepository.save(user);
            return new VerificationCodeRecoveryPasswordDTO(toEmail, recoveryCode);
        }
        SecurityCode securityCode = user.getSecurityCode();
        securityCode.setCode(code);
        securityCode.setStatus(true);
        securityCode.setExpirationTime(LocalDateTime.now().plusMinutes(10));

        securityCodeRepository.save(securityCode);

        return new VerificationCodeRecoveryPasswordDTO(toEmail, code);


    }



}
