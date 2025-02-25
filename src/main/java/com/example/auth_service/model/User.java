package com.example.auth_service.model;


import com.example.auth_service.dto.user.UserRegisterDTO;
import com.example.auth_service.infra.security.config.HmacEncryption;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;



@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;



    private String name;


    private String email;


    private String password;

    private Boolean status;

    private Integer failedAttempts;

    @OneToOne(mappedBy="user")
    private SecurityCode securityCode;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();;



    public User(UserRegisterDTO userRegisterDTO) {
        this.name = userRegisterDTO.username();
        this.email = userRegisterDTO.email();
        this.password = HmacEncryption.encryptKey(userRegisterDTO.password());
        this.status = true;
        this.failedAttempts = 0;
    }

    public void addRole(Role role) {
        roles.add(role);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void increaseFailedLoginAttempts() {
        failedAttempts++;
        if(failedAttempts >= 5) {
            status = false;
        }
    }

    public void resetFailedLoginAttempts() {
        failedAttempts = 0;
    }


}
