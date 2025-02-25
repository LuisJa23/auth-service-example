package com.example.auth_service.service;

import com.example.auth_service.dto.ChangePasswordDTO;
import com.example.auth_service.dto.user.UserRegisterDTO;
import com.example.auth_service.dto.user.UserRegisterResponseDTO;
import com.example.auth_service.dto.user.ValidateSecurityCodeDTO;
import com.example.auth_service.infra.security.config.HmacEncryption;
import com.example.auth_service.model.Role;
import com.example.auth_service.model.SecurityCode;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.RoleRepository;
import com.example.auth_service.repository.SecurityCodeRepository;
import com.example.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityCodeRepository securityCodeRepository;

    public User getUserByEmail(String email) {
        return (User) userRepository.findByEmail(email);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public UserRegisterResponseDTO registerUser(UserRegisterDTO userRegisterDTO)
    {

        Role role = roleRepository.findByName("USER");
        if (role == null) {
            throw new RuntimeException("Role 'USER' not found in database.");
        }



        User user = new User(userRegisterDTO);
        user.addRole(role);
        userRepository.save(user);
        return new UserRegisterResponseDTO(user);
    }

    public void increaseFailedLoginAttempts(User user) {
        if(user.getFailedAttempts() >= 5){
            return;
        }

        user.increaseFailedLoginAttempts();
        userRepository.save(user);
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = (User) userRepository.findByEmail(changePasswordDTO.email());

        SecurityCode securityCode = user.getSecurityCode();


        if (securityCode == null) {
            throw new IllegalArgumentException("Código de seguridad no encontrado.");
        }
        if (securityCode.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Código de seguridad expirado.");
        }

        if (!HmacEncryption.verifyKey(changePasswordDTO.securityCode(), securityCode.getCode())) {
            System.out.println("Expected code (hashed): " + securityCode.getCode());
            System.out.println("Provided code: " + changePasswordDTO.securityCode());

            throw new IllegalArgumentException("Código de seguridad inválido.");
        }





        String password = HmacEncryption.encryptKey(changePasswordDTO.password());

        user.setPassword(password);
        user.resetFailedLoginAttempts();
        user.setStatus(true);

        userRepository.save(user);
    }

    public Boolean isValidateCode(ValidateSecurityCodeDTO validateSecurityCodeDTO) {
        User user = (User) userRepository.findByEmail(validateSecurityCodeDTO.email());

        SecurityCode securityCode = user.getSecurityCode();

        if (securityCode == null || securityCode.getExpirationTime().isBefore(LocalDateTime.now()) || !securityCode.getStatus()) {
            throw new IllegalArgumentException("Código de seguridad inválido.");
        }


        if (!HmacEncryption.verifyKey(validateSecurityCodeDTO.securityCode(), securityCode.getCode())) {

            throw new IllegalArgumentException("Código de seguridad inválido 2.");
        }


        securityCode.setStatus(false);
        securityCodeRepository.save(securityCode);

        return true;

    }
}
