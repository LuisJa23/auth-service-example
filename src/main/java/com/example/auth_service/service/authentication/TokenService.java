package com.example.auth_service.service.authentication;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.auth_service.model.Role;
import com.example.auth_service.model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {


    private String password;

    public String generateToken(User authenticatedUser, User user) {
        password = user.getPassword();



        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            return JWT.create()
                    .withIssuer("auth-service-example") //Este iss debe ser igual
                    .withSubject(authenticatedUser.getUsername())
                    .withClaim("id", authenticatedUser.getId())
                    .withClaim("roles", authenticatedUser.getRoles().stream()
                            .map(Role::getName)
                            .toList())
                    .withExpiresAt(generateExpirationDate()) //Generar un tiempo para que expire
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(password); // validando firma
            verifier = JWT.require(algorithm)
                    .withIssuer("auth-service-example") //Este iss debe ser igual
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

    //ToDo Cambiar el tiempo de expiracion del token
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(720).toInstant(ZoneOffset.of("-05:00"));
    }




}
