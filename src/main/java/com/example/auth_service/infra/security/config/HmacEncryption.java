package com.example.auth_service.infra.security.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HmacEncryption {

    private static final BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    /**
     * Método para encriptar una clave usando BCrypt
     *
     * @param key la clave a encriptar
     * @return la clave encriptada
     */
    public static String encryptKey(String key) {
        // Generar el hash de la clave usando BCrypt
        return bcryptEncoder.encode(key);
    }

    /**
     * Método para verificar si una clave sin encriptar coincide con el hash encriptado
     *
     * @param rawKey     la clave sin encriptar
     * @param hashedKey  la clave encriptada
     * @return true si coinciden, false de lo contrario
     */
    public static boolean verifyKey(String rawKey, String hashedKey) {
        return bcryptEncoder.matches(rawKey, hashedKey);
    }


}
