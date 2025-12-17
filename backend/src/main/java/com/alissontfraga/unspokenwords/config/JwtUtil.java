package com.alissontfraga.unspokenwords.config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtSecret);
    }

    // ----------- GERAR TOKEN -----------
    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .sign(getAlgorithm());
    }

    // ----------- PEGAR USERNAME -----------
    public String getUsernameFromToken(String token) {
        return verifyToken(token).getSubject();
    }

    // ----------- VALIDAR TOKEN -----------
    public boolean validateToken(String token) {
        try {
            verifyToken(token);
            return true;
        } catch (JWTVerificationException ex) {
            log.error("❌ JWT inválido: " + ex.getMessage());
            return false;
        }
    }

    // ----------- FUNÇÃO INTERNA DE VERIFICAÇÃO -----------
    private DecodedJWT verifyToken(String token) {
        return JWT.require(getAlgorithm()).build().verify(token);
    }
}
