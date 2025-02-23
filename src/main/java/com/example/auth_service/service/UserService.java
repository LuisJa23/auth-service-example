package com.example.auth_service.service;

import com.example.auth_service.dto.user.UserRegisterDTO;
import com.example.auth_service.dto.user.UserRegisterResponseDTO;
import com.example.auth_service.model.Role;
import com.example.auth_service.model.User;
import com.example.auth_service.repository.RoleRepository;
import com.example.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
}
