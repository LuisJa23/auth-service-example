package com.example.auth_service.service;

import com.example.auth_service.model.User;
import com.example.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email) {
        return (User) userRepository.findByEmail(email);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void increaseFailedLoginAttempts(User user) {
        if(user.getFailedAttempts() >= 5){
            return;
        }

        user.increaseFailedLoginAttempts();
        userRepository.save(user);
    }
}
