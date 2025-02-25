package com.example.auth_service.repository;


import com.example.auth_service.model.SecurityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityCodeRepository extends JpaRepository<SecurityCode, Long> {
}
