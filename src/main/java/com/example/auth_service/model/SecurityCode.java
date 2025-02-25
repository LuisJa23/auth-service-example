package com.example.auth_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "security_code")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;



    private String code;

    private LocalDateTime expirationTime;

    public Boolean status;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;


    public SecurityCode(String code, User user) {
        this.code = code;
        expirationTime = LocalDateTime.now().plusMinutes(10);
        status = true;
        this.user = user;
    }



}
